package com.navec.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResponse {
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private int totalItems;
}
