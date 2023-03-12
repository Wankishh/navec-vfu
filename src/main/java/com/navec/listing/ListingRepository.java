package com.navec.listing;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ListingRepository extends CrudRepository<Listing, Long> {
    Optional<Listing> findById(Long id);
}
