package com.navec.search;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class SearchRequestMapper {
    private final SortBy sort;
    private final Long brandId;
    private final Long brandModelId;
    private final Integer areaId;
    private final Integer placeId;

    private final Double priceFrom;
    private final Double priceTo;
    private final List<Long> filters;

    private final Integer page;
    private final Integer pageSize;

    public SearchRequestMapper(Map<String, String> requestParams, List<Long> filters) {
        this.sort = requestParams.get("sort") != null ? SortBy.valueOf(requestParams.get("sort")) : SortBy.NEWEST;
        this.brandId = requestParams.get("brandId") != null ? Long.valueOf(requestParams.get("brandId")) : null;
        this.brandModelId = requestParams.get("brandModelId") != null ? Long.valueOf(requestParams.get("brandModelId")) : null;
        this.areaId = requestParams.get("areaId") != null ? Integer.valueOf(requestParams.get("areaId")) : null;
        this.placeId = requestParams.get("placeId") != null ? Integer.valueOf(requestParams.get("placeId")) : null;
        this.priceFrom = requestParams.get("priceFrom") != null ? Double.valueOf(requestParams.get("priceFrom")) : null;
        this.priceTo = requestParams.get("priceTo") != null ? Double.valueOf(requestParams.get("priceTo")) : null;
        this.page = requestParams.get("page") != null ? Integer.parseInt(requestParams.get("page")) : 1;
        this.pageSize = requestParams.get("pageSize") != null ? Integer.parseInt(requestParams.get("pageSize")) : 25;

        this.filters = filters;
    }
}
