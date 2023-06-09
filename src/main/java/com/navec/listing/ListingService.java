package com.navec.listing;

import com.navec.address.area.AreaService;
import com.navec.address.place.PlaceService;
import com.navec.brand.Brand;
import com.navec.brand.BrandService;
import com.navec.brand_model.BrandModel;
import com.navec.environment.Env;
import com.navec.exception.ResponseException;
import com.navec.filter.Filter;
import com.navec.filter.FilterService;
import com.navec.filter.FilterType;
import com.navec.image.ImageService;
import com.navec.listing.request.CreateListingRequest;
import com.navec.listing.response.ListingResponse;
import com.navec.listing.response.PreviewListing;
import com.navec.listing_filter.ListingFilter;
import com.navec.listing_filter.ListingFilterRequest;
import com.navec.listing_filter.ListingFilterService;
import com.navec.section.Section;
import com.navec.section.SectionService;
import com.navec.user.UserService;
import com.navec.utils.PermissionUtils;
import com.navec.utils.TimestampUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ListingService {
    public static final double EUR_TO_BGN_COURSE = 1.956;
    private final AreaService areaService;
    private final PlaceService placeService;
    private final UserService userService;

    private final SectionService sectionService;

    private final ListingRepository listingRepository;

    private final ImageService imageService;

    private final Env env;

    private final BrandService brandService;

    private final FilterService filterService;

    private final ListingFilterService listingFilterService;
    private final PreviewListingAssembler previewListingAssembler;

    public ListingResponse getListing(final Long listingId) throws ResponseException {
        // TODO: fix N+1 problem
        return new ListingResponse(this.findListingById(listingId), this.env.getBaseImageUri());
    }

    public Listing findListingById(Long listingId) {
        return this.listingRepository.findById(listingId)
                .orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND, "Listing not found"));
    }

    public Long createListing(CreateListingRequest createListingRequest) {
        // TODO: fix n+1 problem in brands
        Section section = this.sectionService.findById(createListingRequest.getSectionId());
        Map<Long, Filter> filters = getLongFilterMap(section);

        Listing newListing = new Listing();

        if (!section.getBrands().isEmpty()) {
            if (createListingRequest.getBrandId() == null) {
                throw new ResponseException(HttpStatus.UNPROCESSABLE_ENTITY, "Brand is required");
            }
            this.assignBrandRelations(createListingRequest, newListing);
        }

        this.validateRequestFilters(createListingRequest, filters);
        newListing.setSection(section);
        newListing.setUser(this.userService.getCurrentUser());
        newListing.setWatchers(0);
        this.assignBaseProperties(createListingRequest, newListing);
        Listing savedListing = this.listingRepository.save(newListing);
        savedListing.setImages(
                this.imageService.updateImagesWithListing(createListingRequest.getImages(), savedListing)
        );
        savedListing.setListingFilters(
                this.listingFilterService.createListingFiltersWithListing(createListingRequest.getFilters(), savedListing, filters)
        );
        return savedListing.getId();
    }

    private void assignBaseProperties(CreateListingRequest createListingRequest, Listing newListing) {
        newListing.setTitle(createListingRequest.getTitle());
        newListing.setDescription(createListingRequest.getDescription());
        newListing.setPrice(createListingRequest.getPrice());
        newListing.setPriceBg(createListingRequest.getPrice());
        newListing.setPriceEu(Math.floor(createListingRequest.getPrice() / EUR_TO_BGN_COURSE));
        newListing.setArea(this.areaService.findById(createListingRequest.getAreaId()));
        newListing.setPlace(this.placeService.findById(createListingRequest.getPlaceId()));
        newListing.setYoutubeUrl(createListingRequest.getYoutubeUrl());
        newListing.setCreatedAt(TimestampUtils.getCurrentTimestamp());
        newListing.setUpdatedAt(TimestampUtils.getCurrentTimestamp());
        newListing.setCurrency(createListingRequest.getCurrency());
    }

    @Transactional
    public Long updateListing(CreateListingRequest updateListingRequest, Long listingId) {
        // TOOD: Fix N+1 problem
        Listing listing = this.findListingById(listingId);
        Section section = this.sectionService.findById(updateListingRequest.getSectionId());
        Map<Long, Filter> filters = getLongFilterMap(section);

        if (PermissionUtils.isMissingPermission(this.userService.getCurrentUser(), listing.getUser().getId())) {
            throw new ResponseException(HttpStatus.FORBIDDEN);
        }

        if (!section.getBrands().isEmpty() && updateListingRequest.getBrandId() == null) {
            throw new ResponseException(HttpStatus.UNPROCESSABLE_ENTITY, "Brand is required");
        }

        this.validateRequestFilters(updateListingRequest, this.getLongFilterMap(section));

        if (!Objects.equals(updateListingRequest.getBrandId(), listing.getBrand().getId())) {
            this.assignBrandRelations(updateListingRequest, listing);
        }
        this.assignBaseProperties(updateListingRequest, listing);
        Listing savedListing = this.listingRepository.save(listing);
        this.imageService.removeImagesForListing(listing.getId());
        savedListing.setImages(
                this.imageService.updateImagesWithListing(updateListingRequest.getImages(), savedListing)
        );
        this.listingFilterService.removeByListingId(listing);
        savedListing.setListingFilters(
                this.listingFilterService.createListingFiltersWithListing(
                        updateListingRequest.getFilters(),
                        savedListing,
                        filters)
        );
        return listing.getId();
    }

    private Map<Long, Filter> getLongFilterMap(Section section) {
        Map<Long, Filter> filters = new HashMap<>();
        this.filterService.getFiltersBySection(section.getId())
                .forEach(f -> filters.put(f.getId(), f));
        return filters;
    }

    private void validateRequestFilters(CreateListingRequest createListingRequest, Map<Long, Filter> filters) {
        Set<Long> allIncomingFilterIds = createListingRequest.getFilters().stream()
                .map(ListingFilterRequest::getFilterId)
                .collect(Collectors.toSet());

        HashMap<String, String> errors = new HashMap<>();

        filters.forEach((key, value) -> {
            if (Boolean.TRUE.equals(value.getRequired())) {
                boolean exist = allIncomingFilterIds.contains(value.getId());
                if (!exist) {
                    errors.put(value.getName(), "Required");
                }
            }
        });

        if (!errors.isEmpty()) {
            throw new ResponseException(HttpStatus.UNPROCESSABLE_ENTITY, "Missing information for filters", errors);
        }

        boolean allMustExist = createListingRequest.getFilters().
                stream()
                .allMatch(incomingFilter -> {
                    Filter realFilter = filters.get(incomingFilter.getFilterId());
                    if (realFilter == null) {
                        errors.put("filter-" + incomingFilter.getFilterId().toString(), "Not existing");
                        return false;
                    }

                    if (realFilter.getType() == FilterType.NORMAL) {
                        return realFilter.getFilterOptions().
                                stream()
                                .anyMatch(l -> Objects.equals(l.getId(), incomingFilter.getFilterOptionId()));
                    }

                    return true;
                });

        if (!allMustExist) {
            throw new ResponseException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid filter or filter option");
        }
    }

    private void assignBrandRelations(CreateListingRequest createListingRequest, Listing newListing) {
        Brand brand = this.brandService.findBrandById(createListingRequest.getBrandId());

        BrandModel brandModel = brand.getModels().stream()
                .filter(bm -> Objects.equals(bm.getId(), createListingRequest.getBrandModelId()))
                .findFirst()
                .orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND, "Brand model not found"));

        newListing.setBrand(brand);
        newListing.setBrandModel(brandModel);
    }

    public List<PreviewListing> getLastCreated() {
        List<Listing> listings = this.listingRepository.getLatCreatedListings();
        List<ListingFilter> listingFilters = this.listingFilterService.getMultipleListings(getListingIds(listings));
        return listings.stream()
                .map(l ->
                        this.previewListingAssembler.assemble(
                                l, getListingFilters(listingFilters, l)
                        )
                )
                .toList();
    }

    public List<PreviewListing> getTopViewed() {
        List<Listing> listings = this.listingRepository.getTopViewed();
        List<ListingFilter> listingFilters = this.listingFilterService.getMultipleListings(getListingIds(listings));

        return listings.stream()
                .map(l ->
                        this.previewListingAssembler.assemble(
                                l, getListingFilters(listingFilters, l)
                        )
                )
                .toList();
    }

    public void deleteListing(Long listingId) {
        Listing listing = this.findListingById(listingId);
        listing.setDeletedAt(TimestampUtils.getCurrentTimestamp());
        this.listingRepository.save(listing);
    }


    private List<Long> getListingIds(List<Listing> listings) {
        return listings.stream().map(Listing::getId).toList();
    }

    private List<ListingFilter> getListingFilters(List<ListingFilter> listingFilters, Listing l) {
        return listingFilters.stream()
                .filter(f -> Objects.equals(f.getListing().getId(), l.getId()))
                .toList();
    }
}
