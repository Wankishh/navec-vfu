package com.navec.listing.filter;

import com.navec.exception.ResponseException;
import com.navec.listing.section.SectionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterService {
    private final SectionService sectionService;
    private final FilterRepository filterRepository;

    public FilterService(SectionService sectionService,
                         FilterRepository filterRepository) {
        this.sectionService = sectionService;
        this.filterRepository = filterRepository;
    }


    public List<FilterDto> getFiltersBySection(Long sectionId) throws ResponseException {
        List<Filter> filters = this.filterRepository.findBySectionId(sectionId);

        return filters.stream()
                .map(FilterDto::new)
                .toList();
    }
}
