package com.navec.listing.filter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/filters")
public class FilterController {

    private final FilterService filterService;

    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    @GetMapping(path = "/{section_id}")
    public ResponseEntity<List<FilterDto>> getFiltersBySections(@PathVariable("section_id") Long sectionId) {
        return ResponseEntity.ok(this.filterService.getFiltersBySection(sectionId));
    }
}
