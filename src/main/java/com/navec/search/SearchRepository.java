package com.navec.search;

import com.navec.listing.Listing;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SearchRepository extends PagingAndSortingRepository<Listing, Long> {
}
