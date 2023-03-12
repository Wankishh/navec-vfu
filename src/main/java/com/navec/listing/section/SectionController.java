package com.navec.listing.section;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sections")
public class SectionController {
    private final SectionService sectionsService;

    public SectionController(SectionService sectionsService) {
        this.sectionsService = sectionsService;
    }


    @GetMapping(path = "")
    public ResponseEntity<List<SectionDto>> getSections() {
        return ResponseEntity.ok(this.sectionsService.getSections());
    }
}
