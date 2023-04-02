package com.navec.search;

import jakarta.persistence.criteria.From;
import lombok.Getter;
import software.amazon.ion.Decimal;

import java.util.List;
import java.util.Map;

@Getter
public class SearchRequestMapper {
    private final SortBy sort;
    private final Long brandId;
    private final Long brandModelId;
    private final Integer areaId;
    private final Integer placeId;

    private final Decimal priceFrom;
    private final Decimal priceTo;
    private final List<Long> filters;

    private final Integer page;
    private final Integer pageSize;

    public SearchRequestMapper(Map<String, String> requestParams, List<Long> filters) {
        this.sort = requestParams.get("sort") != null ? SortBy.valueOf(requestParams.get("sort")) : SortBy.NEWEST;
        this.brandId = requestParams.get("brandId") != null ? Long.valueOf(requestParams.get("brandId")) : null;
        this.brandModelId = requestParams.get("brandModelId") != null ? Long.valueOf(requestParams.get("brandModelId")) : null;
        this.areaId = requestParams.get("areaId") != null ? Integer.valueOf(requestParams.get("areaId")) : null;
        this.placeId = requestParams.get("placeId") != null ? Integer.valueOf(requestParams.get("placeId")) : null;
        this.priceFrom = requestParams.get("priceFrom") != null ? Decimal.valueOf(requestParams.get("priceFrom")) : null;
        this.priceTo = requestParams.get("priceTo") != null ? Decimal.valueOf(requestParams.get("priceTo")) : null;
        this.page = requestParams.get("page") != null ? Integer.valueOf(requestParams.get("page")) : 1;
        this.pageSize = requestParams.get("pageSize") != null ? Integer.valueOf(requestParams.get("pageSize")) : 25;

        this.filters = filters;
    }
}
