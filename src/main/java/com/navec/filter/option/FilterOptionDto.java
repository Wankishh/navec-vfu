package com.navec.filter.option;

import lombok.Data;

@Data
public class FilterOptionDto {
    private Long id;
    private String name;

    public FilterOptionDto (FilterOption filterOption) {
        this.id = filterOption.getId();
        this.name = filterOption.getName();
    }
}
