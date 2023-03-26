package com.navec.listing_filter;

import com.navec.listing.Listing;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingFilterRepository extends JpaRepository<ListingFilter, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = "listing-filter")
    List<ListingFilter> findByListingId(Long listingId);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = "listing-filter")
    List<ListingFilter> findByListingIdIn(List<Long> listingIds);

    void deleteByListing(Listing listing);
}