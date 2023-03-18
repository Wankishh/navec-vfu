package com.navec.section;

import org.springframework.data.repository.CrudRepository;

public interface SectionRepository extends CrudRepository<Section, Long> {
//    @Query("select s, b, bm, bms from Section s JOIN s.brands b JOIN b.models bm JOIN bm.serie bms")
//    Collection<Section> getAllWithRelations();
}
