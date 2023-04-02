package com.navec.extras;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class ExtraDto {
    private final Long id;
    private final String name;
    private final String nameEn;
}
