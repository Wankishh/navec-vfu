package com.navec.listing;


import com.navec.exception.ResponseException;
import com.navec.listing.request.CreateListingRequest;
import com.navec.listing.response.ListingResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/listings")
@Tag(name = "Listings")
public class ListingController {
    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }


    @GetMapping(path = "/{listing_id}")
    public ResponseEntity<ListingResponse> getListing(
            @PathVariable("listing_id") Long listingId
    ) throws ResponseException {
        return ResponseEntity.ok(this.listingService.getListing(listingId));
    }

    @SecurityRequirement(name = "apiAuth")
    @PostMapping(path = "")
    public ResponseEntity<Object> createListing(
            @Valid @RequestBody CreateListingRequest createListingRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.listingService.createListing(createListingRequest));
    }
}
