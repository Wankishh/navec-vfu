package com.navec.filter;

import com.navec.filter.option.FilterOptionDto;
import lombok.Data;

import java.util.List;

@Data
public class FilterDto {
    private Long id;
    private FilterType type;
    private InputType inputType;
    private String name;
    private List<FilterOptionDto> filterOptions;

    private Boolean required;

    public FilterDto(Filter filter) {
        this.id = filter.getId();
        this.type = filter.getType();
        this.inputType = filter.getInputType();
        this.name = filter.getName();
        this.required = filter.getRequired();

        if (this.type == FilterType.NORMAL) {
            this.filterOptions = filter.getFilterOptions()
                    .stream()
                    .map(FilterOptionDto::new)
                    .toList();
        }
    }
}
