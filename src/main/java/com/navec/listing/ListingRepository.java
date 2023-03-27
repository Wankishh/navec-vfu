package com.navec.listing;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ListingRepository extends CrudRepository<Listing, Long> {
    Optional<Listing> findById(Long id);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = "searched-listings")
    @Query("SELECT l FROM Listing l WHERE l.section.id = 1 AND l.deletedAt = null ORDER BY l.updatedAt DESC LIMIT 4")
    List<Listing> getLatCreatedListings();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = "searched-listings")
    @Query("SELECT l FROM Listing l WHERE l.section.id = 1 AND l.deletedAt = null ORDER BY l.watchers DESC LIMIT 2")
    List<Listing> getTopViewed();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = "searched-listings")
    List<Listing> findByUserId(Long userId, Pageable pageable);
}
