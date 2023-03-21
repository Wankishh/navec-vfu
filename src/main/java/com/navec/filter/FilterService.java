package com.navec.filter;

import com.navec.exception.ResponseException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterService {
    private final FilterRepository filterRepository;

    public FilterService(FilterRepository filterRepository) {
        this.filterRepository = filterRepository;
    }


    public List<FilterDto> getFilterDtosBySection(Long sectionId) throws ResponseException {
        List<Filter> filters = getFiltersBySection(sectionId);

        return filters.stream()
                .map(FilterDto::new)
                .toList();
    }

    public List<Filter> getFiltersBySection(Long sectionId) {
        return this.filterRepository.findBySectionId(sectionId);
    }
}
