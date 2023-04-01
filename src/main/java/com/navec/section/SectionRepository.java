package com.navec.section;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends CrudRepository<Section, Long> {
    @Override
    List<Section> findAll();

    @Override
    Optional<Section> findById(Long id);
}
