package com.navec.filter;

import com.navec.filter.option.FilterOptionDto;
import lombok.Data;

import java.util.List;

@Data
public class FilterDto {
    private Long id;
    private FilterType filterType;
    private InputType inputType;
    private String name;
    private List<FilterOptionDto> filterOptions;

    public FilterDto(Filter filter) {
        this.id = filter.getId();
        this.filterType = filter.getType();
        this.inputType = filter.getInputType();
        this.name = filter.getName();

        if (this.filterType == FilterType.NORMAL) {
            this.filterOptions = filter.getFilterOptions()
                    .stream()
                    .map(FilterOptionDto::new)
                    .toList();
        }
    }
}
