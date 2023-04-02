package com.navec.search;

import com.navec.listing.Listing;
import com.navec.listing.PreviewListingAssembler;
import com.navec.listing.response.PreviewListing;
import com.navec.listing_filter.ListingFilter;
import com.navec.listing_filter.ListingFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchDao searchDao;
    private final PreviewListingAssembler previewListingAssembler;
    private final ListingFilterService listingFilterService;

    public SearchResponse search(SearchRequestMapper searchRequestMapper) {
        SearchDaoResponse searchDaoResponse = searchDao.search(searchRequestMapper);
        List<ListingFilter> listingFilters = getListingFilters(searchDaoResponse);
        List<PreviewListing> previewListings = getPreviewListings(searchDaoResponse, listingFilters);
        PageResponse pageResponse = getPageResponse(searchRequestMapper, searchDaoResponse);
        return getSearchResponse(previewListings, pageResponse);
    }

    private List<ListingFilter> getListingFilters(SearchDaoResponse searchDaoResponse) {
        return listingFilterService.getMultipleListings(
                searchDaoResponse.data().stream().map(Listing::getId).toList()
        );
    }

    private List<PreviewListing> getPreviewListings(SearchDaoResponse searchDaoResponse, List<ListingFilter> listingFilters) {
        return searchDaoResponse.data().stream()
                .map(previewListing -> {
                    List<ListingFilter> currentListingFilters = listingFilters.stream()
                            .filter(listingFilter -> listingFilter.getListing().getId().equals(previewListing.getId()))
                            .toList();
                    return previewListingAssembler.assemble(previewListing, currentListingFilters);
                })
                .toList();
    }

    private SearchResponse getSearchResponse(List<PreviewListing> previewListings, PageResponse pageResponse) {
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setData(previewListings);
        searchResponse.setPagination(pageResponse);
        return searchResponse;
    }

    private PageResponse getPageResponse(SearchRequestMapper searchRequestMapper, SearchDaoResponse searchDaoResponse) {
        PageResponse pageResponse = new PageResponse();
        pageResponse.setCurrentPage(searchRequestMapper.getPage());
        pageResponse.setPageSize(searchRequestMapper.getPageSize());
        pageResponse.setTotalItems(searchDaoResponse.totalItems());

        int totalPages = (int) Math.ceil((double) searchDaoResponse.totalItems() / searchRequestMapper.getPageSize());
        pageResponse.setTotalPages(totalPages);
        return pageResponse;
    }
}
