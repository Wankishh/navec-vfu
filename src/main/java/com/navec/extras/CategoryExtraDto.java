package com.navec.extras;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CategoryExtraDto {
    private final Long id;
    private final String name;
    private final String nameEn;
    private final List<ExtraDto> extras;
}
