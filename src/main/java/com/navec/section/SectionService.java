package com.navec.section;

import com.navec.brand.Brand;
import com.navec.brand.BrandDto;
import com.navec.brand.BrandService;
import com.navec.exception.ResponseException;
import com.navec.extras.CategoryExtraDto;
import com.navec.extras.ExtrasService;
import com.navec.filter.FilterDto;
import com.navec.filter.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final FilterService filterService;
    private final BrandService brandService;
    private final ExtrasService extrasService;

    public Section findById(final Long id) {
        return this.sectionRepository.findById(id)
                .orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND));
    }

    public List<SectionDto> getSections() {
        List<Section> sections = this.sectionRepository.findAll();
        List<Brand> brands = this.brandService.findBySections(sections.stream()
                .map(Section::getId)
                .toList());
        List<SectionDto> sectionDtoList = new ArrayList<>();
        sections.forEach(s -> {
            SectionDto sectionDto = new SectionDto(s);
            List<BrandDto> sectionBrands = brands.stream().filter(b -> b.getSection().getId().equals(s.getId()))
                    .map(BrandDto::new)
                    .toList();
            sectionDto.setBrands(sectionBrands);
            sectionDtoList.add(sectionDto);
        });
        return sectionDtoList;
    }

    public SectionDto getSection(Long sectionId) {
        Section section = this.findById(sectionId);
        List<Brand> sectionBrands = this.brandService.findBySectionId(section.getId());
        List<BrandDto> brands = sectionBrands.stream()
                .map(BrandDto::new)
                .toList();
        List<FilterDto> filters = this.filterService.getFilterDtosBySection(sectionId);
        List<CategoryExtraDto> categoryExtras = this.extrasService.findBySectionId(sectionId);
        SectionDto sectionDto = new SectionDto(section);
        sectionDto.setBrands(brands);
        sectionDto.setFilters(filters);
        sectionDto.setCategoryExtras(categoryExtras);
        return sectionDto;
    }
}
