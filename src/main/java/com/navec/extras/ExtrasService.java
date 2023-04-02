package com.navec.extras;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtrasService {

    private final CategoryExtraRepository categoryExtraRepository;

    public List<CategoryExtraDto> findBySectionId(Long sectionId) {
        List<CategoryExtra> categoryExtras = categoryExtraRepository.findBySectionId(sectionId);
        return categoryExtras.stream()
                .map(ExtrasService::assembleCategoryExtraDto)
                .toList();
    }

    private static CategoryExtraDto assembleCategoryExtraDto(CategoryExtra categoryExtra) {
        return CategoryExtraDto.builder()
                .id(categoryExtra.getId())
                .name(categoryExtra.getName())
                .nameEn(categoryExtra.getNameEn())
                .extras(categoryExtra.getExtras().stream()
                        .map(ExtrasService::assembleExtraDto)
                        .toList())
                .build();
    }

    private static ExtraDto assembleExtraDto(Extra extra) {
        return ExtraDto.builder()
                .id(extra.getId())
                .name(extra.getName())
                .nameEn(extra.getNameEn())
                .build();
    }
}
