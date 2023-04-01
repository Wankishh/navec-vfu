package com.navec.brand;


import com.navec.exception.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Brand findBrandById(Long brandId) {
        return this.brandRepository.findById(brandId)
                .orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND, "Brand was not found"));
    }

    public List<Brand> findBySections(List<Long> sectionIds) {
        return this.brandRepository.findAllBySectionIdIn(sectionIds);
    }

    public List<Brand> findBySectionId(Long sectionId) {
        return this.brandRepository.findAllBySectionId(sectionId);
    }
}
