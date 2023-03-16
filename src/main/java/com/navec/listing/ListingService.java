package com.navec.listing;

import com.navec.address.area.AreaService;
import com.navec.address.place.PlaceService;
import com.navec.environment.Env;
import com.navec.exception.ResponseException;
import com.navec.image.ImageService;
import com.navec.listing.request.CreateListingRequest;
import com.navec.listing.response.ListingResponse;
import com.navec.listing.section.SectionService;
import com.navec.user.UserService;
import com.navec.utils.TimestampUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    public ListingService(AreaService areaService,
                          PlaceService placeService,
                          UserService userService,
                          SectionService sectionService,
                          ListingRepository listingRepository,
                          ImageService imageService,
                          Env env) {
        this.areaService = areaService;
        this.placeService = placeService;
        this.userService = userService;
        this.sectionService = sectionService;
        this.listingRepository = listingRepository;
        this.imageService = imageService;
        this.env = env;

    }

    public ListingResponse getListing(final Long listingId) throws ResponseException {
        return new ListingResponse(findListingById(listingId), this.env.getGetBaseImageUri());
    }

    public Listing findListingById(Long listingId) {
        return this.listingRepository.findById(listingId)
                .orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND, "Listing not found"));
    }

    public ListingResponse createListing(CreateListingRequest createListingRequest) {
        log.debug(createListingRequest.getDescription());
        Listing newListing = new Listing();
        newListing.setUser(this.userService.getCurrentUser());
        newListing.setTitle(createListingRequest.getTitle());
        newListing.setDescription(createListingRequest.getDescription());
        newListing.setPrice(createListingRequest.getPrice());
        newListing.setPriceBg(createListingRequest.getPrice());
        newListing.setPriceEu(Math.floor(createListingRequest.getPrice() / EUR_TO_BGN_COURSE));
        newListing.setSection(this.sectionService.findById(createListingRequest.getSectionId()));
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
        return new ListingResponse(savedListing, this.env.getGetBaseImageUri());
    }
}
