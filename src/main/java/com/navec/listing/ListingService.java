package com.navec.listing;

import com.navec.address.area.Area;
import com.navec.address.area.AreaService;
import com.navec.address.place.Place;
import com.navec.address.place.PlaceService;
import com.navec.brand.Brand;
import com.navec.brand.BrandService;
import com.navec.brand_model.BrandModel;
import com.navec.environment.Env;
import com.navec.exception.ResponseException;
import com.navec.filter.Filter;
import com.navec.filter.FilterService;
import com.navec.filter.FilterType;
import com.navec.image.Image;
import com.navec.image.ImageDto;
import com.navec.image.ImageService;
import com.navec.listing.request.CreateListingRequest;
import com.navec.listing_filter.ListingFilterRequest;
import com.navec.listing.response.ListingResponse;
import com.navec.listing.response.PreviewListing;
import com.navec.listing_filter.ListingFilterService;
import com.navec.section.Section;
import com.navec.section.SectionService;
import com.navec.user.UserService;
import com.navec.utils.TimestampUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
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

    public ListingService(AreaService areaService,
                          PlaceService placeService,
                          UserService userService,
                          SectionService sectionService,
                          ListingRepository listingRepository,
                          ImageService imageService,
                          Env env,
                          BrandService brandService,
                          FilterService filterService,
                          ListingFilterService listingFilterService) {
        this.areaService = areaService;
        this.placeService = placeService;
        this.userService = userService;
        this.sectionService = sectionService;
        this.listingRepository = listingRepository;
        this.imageService = imageService;
        this.env = env;
        this.brandService = brandService;
        this.filterService = filterService;
        this.listingFilterService = listingFilterService;
    }

    public ListingResponse getListing(final Long listingId) throws ResponseException {
        return new ListingResponse(findListingById(listingId), this.env.getBaseImageUri());
    }

    public Listing findListingById(Long listingId) {
        return this.listingRepository.findById(listingId)
                .orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND, "Listing not found"));
    }

    public Long createListing(CreateListingRequest createListingRequest) {
        Section section = this.sectionService.findById(createListingRequest.getSectionId());
        Map<Long, Filter> filters = getLongFilterMap(section);

        Listing newListing = new Listing();

        if(!section.getBrands().isEmpty()) {
            if(createListingRequest.getBrandId() == null) {
                throw  new ResponseException(HttpStatus.UNPROCESSABLE_ENTITY, "Brand is required");
            }

            this.assignBrandRelations(createListingRequest, newListing);
        }

        this.validateRequestFilters(createListingRequest, filters);

        newListing.setUser(this.userService.getCurrentUser());
        newListing.setTitle(createListingRequest.getTitle());
        newListing.setDescription(createListingRequest.getDescription());
        newListing.setPrice(createListingRequest.getPrice());
        newListing.setPriceBg(createListingRequest.getPrice());
        newListing.setPriceEu(Math.floor(createListingRequest.getPrice() / EUR_TO_BGN_COURSE));
        newListing.setSection(section);
        newListing.setArea(this.areaService.findById(createListingRequest.getAreaId()));
        newListing.setPlace(this.placeService.findById(createListingRequest.getPlaceId()));
        newListing.setYoutubeUrl(createListingRequest.getYoutubeUrl());
        newListing.setCreatedAt(TimestampUtils.getCurrentTimestamp());
        newListing.setUpdatedAt(TimestampUtils.getCurrentTimestamp());
        newListing.setCurrency(createListingRequest.getCurrency());
        newListing.setArchived(false);
        newListing.setWatchers(0);
        Listing savedListing = this.listingRepository.save(newListing);
        savedListing.setImages(
                this.imageService.updateImagesWithListing(createListingRequest.getImages(), savedListing)
        );
        savedListing.setListingFilters(
                this.listingFilterService.createListingFiltersWithListing(createListingRequest.getFilters(), savedListing, filters)
        );
        return savedListing.getId();
    }



    private Map<Long, Filter> getLongFilterMap(Section section) {
        Map<Long, Filter> filters = new HashMap<>();

        this.filterService.getFiltersBySection(section.getId())
                .forEach(f -> filters.put(f.getId(), f));
        return filters;
    }

    private void validateRequestFilters(CreateListingRequest createListingRequest, Map<Long, Filter>  filters) {

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

                    if(realFilter.getType() == FilterType.NORMAL) {
                        return realFilter.getFilterOptions().
                                stream()
                                .anyMatch(l -> Objects.equals(l.getId(), incomingFilter.getFilterOptionId()));
                    }

                    // TODO: Validate other types of Filters based on factory
                    return true;
                });

        if(!allMustExist) {
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
        return listings.stream()
                .map(this::createPreviewListing)
                .toList();
    }

    public List<PreviewListing> getTopViewed() {
        List<Listing> listings = this.listingRepository.getTopViewed();
        return listings.stream()
                .map(this::createPreviewListing)
                .toList();
    }

    private PreviewListing createPreviewListing(Listing listing) {
        Image image = listing.getImages() != null && !listing.getImages().isEmpty() ?
                listing.getImages().get(0) : null;
        String publishedFrom = "частно лице";
        String placeTitle = this.extractPlaceTitle(listing);
        List<String> footer = new ArrayList<>();
        return PreviewListing.builder()
                .id(listing.getId())
                .title(listing.getTitle())
                .price(listing.getPrice())
                .priceEu(listing.getPriceEu())
                .currency(listing.getCurrency())
                .publishedFrom(publishedFrom)
                .placeTitle(placeTitle)
                .image(image != null ? new ImageDto(image, this.env.getBaseImageUri()) : null)
                .footer(footer)
                .build();
    }

    private String extractPlaceTitle(Listing listing) {
        Place place = listing.getPlace();
        Area area = listing.getArea();

        if(place != null) {
            return place.getName();
        }
        if(area != null) {
            return area.getName();
        }

        return "";
    }
}
