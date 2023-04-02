package com.navec.listing;

import com.navec.address.area.Area;
import com.navec.address.place.Place;
import com.navec.environment.Env;
import com.navec.filter.FilterType;
import com.navec.image.Image;
import com.navec.image.ImageDto;
import com.navec.listing.response.PreviewListing;
import com.navec.listing_filter.ListingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class PreviewListingAssembler {
    private final Env env;

    public PreviewListing assemble(Listing listing, List<ListingFilter> listingFilters) {
        Image image = listing.getImages() != null && !listing.getImages().isEmpty() ?
                listing.getImages().get(0) : null;
        String publishedFrom = "частно лице";
        String placeTitle = this.extractPlaceTitle(listing);
        List<String> footer = this.extractPreviewFooter(listingFilters);
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

    public PreviewListing assembleWithOwnFilters(Listing listing) {
        return this.assemble(listing, listing.getListingFilters());
    }

    private List<String> extractPreviewFooter(List<ListingFilter> listingFilters) {
        return listingFilters.stream()
                .filter(lf -> lf.getFilter().getShowInPreview())
                .map(lf -> {

                    if (lf.getFilter().getType() == FilterType.NORMAL) {
                        return lf.getFilterOption().getName();
                    }

                    return lf.getValue();
                })
                .toList();
    }

    private String extractPlaceTitle(Listing listing) {
        Place place = listing.getPlace();
        Area area = listing.getArea();

        if (place != null) {
            return place.getName();
        }
        if (area != null) {
            return area.getName();
        }

        return "";
    }
}
