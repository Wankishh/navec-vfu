package com.navec.brand;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = "with-models")
    @Override
    Optional<Brand> findById(Long id);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = "with-models")
    List<Brand> findAllBySectionId(Long sectionId);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = "with-models")

    List<Brand> findAllBySectionIdIn(List<Long> sectionIds);
}