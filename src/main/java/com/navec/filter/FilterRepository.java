package com.navec.filter;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilterRepository extends JpaRepository<Filter, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = "filter-with-options")
    List<Filter> findBySectionId(Long sectionId);
}