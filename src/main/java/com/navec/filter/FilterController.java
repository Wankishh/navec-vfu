package com.navec.filter;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/filters")
@Tag(name = "Filters")
public class FilterController {

    private final FilterService filterService;

    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    @GetMapping(path = "/{sectionId}")
    public ResponseEntity<List<FilterDto>> getFiltersBySections(@PathVariable Long sectionId) {
        return ResponseEntity.ok(this.filterService.getFilterDtosBySection(sectionId));
    }
}
