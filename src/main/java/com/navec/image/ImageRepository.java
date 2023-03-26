package com.navec.image;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository  extends JpaRepository<Image, Long> {
    void deleteByListingId(Long listingId);
}
