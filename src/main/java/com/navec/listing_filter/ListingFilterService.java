package com.navec.listing_filter;

import com.navec.exception.ResponseException;
import com.navec.filter.Filter;
import com.navec.filter.FilterType;
import com.navec.filter.option.FilterOption;
import com.navec.listing.Listing;
import com.navec.utils.TimestampUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ListingFilterService {

    private final ListingFilterRepository listingFilterRepository;


    public ListingFilterService(ListingFilterRepository listingFilterRepository) {
        this.listingFilterRepository = listingFilterRepository;
    }

    public List<ListingFilter> createListingFiltersWithListing(
            List<ListingFilterRequest> incomingFilters,
            Listing savedListing,
            Map<Long, Filter> filters
    ) {
        List<ListingFilter> resultFilters = incomingFilters.stream().map(cf -> {
            Filter filter = filters.get(cf.getFilterId());
            ListingFilter newListingFilter = new ListingFilter();
            newListingFilter.setListing(savedListing);
            newListingFilter.setFilter(filter);
            if (filter.getType() == FilterType.NORMAL) {
                newListingFilter.setFilterOption(
                        this.getFilterOptionFromFilters(filter, cf.getFilterOptionId())
                );
            } else {
                newListingFilter.setValue(cf.getValue());
            }

            newListingFilter.setCreatedAt(TimestampUtils.getCurrentTimestamp());
            newListingFilter.setUpdatedAt(TimestampUtils.getCurrentTimestamp());
            return newListingFilter;
        }).toList();

        this.listingFilterRepository.saveAll(resultFilters);
        return resultFilters;

    }

    private FilterOption getFilterOptionFromFilters(Filter filter, Long filterOptionId) {
        return filter.getFilterOptions()
                .stream()
                .filter(l -> Objects.equals(l.getId(), filterOptionId))
                .findFirst()
                .orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND));
    }

    public List<ListingFilter> findByListingId(Long listingId) {
        return this.listingFilterRepository.findByListingId(listingId);
    }

    public List<ListingFilter> getMultipleListings(List<Long> listingIds) {
        return this.listingFilterRepository.findByListingIdIn(listingIds);
    }

    public void removeByListingId(Listing listing) {
        this.listingFilterRepository.deleteByListing(listing);
    }

    public List<Long> findByFilterOptionIds(List<Long> filterOptionIds) {
        return this.listingFilterRepository.findAllListingIdsByFilterOptionIdIn(filterOptionIds);
    }
}
