package com.navec.listing_filter;


import lombok.Data;

@Data
public class ListingFilterRequest {
    private Long filterId;
    private Long filterOptionId;
    private String value;
}
