package com.navec.extras;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryExtraRepository extends JpaRepository<CategoryExtra, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = "with-extras")
    List<CategoryExtra> findBySectionId(Long sectionId);
}