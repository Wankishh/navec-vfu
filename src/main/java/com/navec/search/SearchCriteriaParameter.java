package com.navec.search;

import com.navec.listing.Listing;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;

public record SearchCriteriaParameter(SearchRequestMapper searchRequestMapper, Root<Listing> root,
                                      CriteriaBuilder criteriaBuilder) {
}
