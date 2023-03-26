package com.navec.listing_filter;

import com.navec.filter.FilterDto;
import com.navec.filter.option.FilterOptionDto;
import lombok.Data;

@Data
public class ListingFilterDto {
    public final Long id;

    public final FilterDto filter;
    public final FilterOptionDto filterOption;

    public final String value;

    public ListingFilterDto(ListingFilter listingFilter) {
        this.id = listingFilter.getId();
        this.filter = new FilterDto(listingFilter.getFilter());
        this.filterOption = listingFilter.getFilterOption() != null ? new FilterOptionDto(listingFilter.getFilterOption()) : null;
        this.value = listingFilter.getValue();
    }
}
