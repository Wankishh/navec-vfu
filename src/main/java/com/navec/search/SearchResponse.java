package com.navec.search;

import com.navec.listing.response.PreviewListing;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchResponse {
   private List<PreviewListing> data;
   private PageResponse pagination;
}
