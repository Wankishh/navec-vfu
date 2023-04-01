package com.navec.search;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/search")
public class SearchController {
    @GetMapping(path = "")
    public ResponseEntity<Object> search(@RequestParam Map<String, String> requestParams, @RequestParam(required = false) List<Long> filters) {
        SearchRequestMapper searchRequestMapper = new SearchRequestMapper(requestParams, filters);
        return ResponseEntity.ok(searchRequestMapper);
    }
}
