package com.navec.search;

import com.navec.listing.Listing;
import com.navec.listing_filter.ListingFilterService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchDao {

    public static final String PRICE_COLUMN = "price";
    private final EntityManager em;

    private final ListingFilterService listingFilterService;

    public SearchDaoResponse search(SearchRequestMapper searchRequestMapper) {
        List<Long> listingIdsFromFilter = getListingIdsFromFilter(searchRequestMapper);

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Listing> root = criteriaQuery.from(Listing.class);

        SearchCriteriaParameter searchCriteriaParameter = new SearchCriteriaParameter(searchRequestMapper, root, criteriaBuilder);
        List<Predicate> predicates = this.createPredicates(searchCriteriaParameter);
        Predicate[] predicatesArray = new Predicate[predicates.size()];
        predicates.toArray(predicatesArray);
        criteriaQuery.where(predicatesArray);

        if(!listingIdsFromFilter.isEmpty())  {
            criteriaQuery.where(root.get("id").in(listingIdsFromFilter));
        }

        criteriaQuery.select(root.get("id"));
        List<Long> resultList = em.createQuery(criteriaQuery).getResultList();
        return getSearchDaoResponse(searchRequestMapper, resultList.size(), listingIdsFromFilter);
    }

    private SearchDaoResponse getSearchDaoResponse(SearchRequestMapper searchRequestMapper, int totalItems, List<Long> listingIdsFromFilter) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Listing> listingCriteriaQuery = criteriaBuilder.createQuery(Listing.class);
        Root<Listing> listingsRoot = listingCriteriaQuery.from(Listing.class);

        listingsRoot.fetch("images", JoinType.LEFT);
        listingsRoot.fetch("place");
        listingsRoot.fetch("area");

        if (searchRequestMapper.getSort() == SortBy.NEWEST) {
            listingCriteriaQuery.orderBy(criteriaBuilder.desc(listingsRoot.get("createdAt")));
        } else if (searchRequestMapper.getSort() == SortBy.PRICE_ASC) {
            listingCriteriaQuery.orderBy(criteriaBuilder.asc(listingsRoot.get(PRICE_COLUMN)));
        } else if (searchRequestMapper.getSort() == SortBy.PRICE_DESC) {
            listingCriteriaQuery.orderBy(criteriaBuilder.desc(listingsRoot.get(PRICE_COLUMN)));
        }

        int page = searchRequestMapper.getPage() > 0 ? searchRequestMapper.getPage() - 1 : 0;
        int limit = page == 0 ? searchRequestMapper.getPageSize() : searchRequestMapper.getPageSize() * (page + 1) + 1;
        int offset = page == 0 ? 0 : page + searchRequestMapper.getPageSize() - 1;

        listingCriteriaQuery.where(listingsRoot.get("id").in(listingIdsFromFilter));

        TypedQuery<Listing> query = em.createQuery(listingCriteriaQuery)
                .setMaxResults(limit)
                .setFirstResult(offset);
        List<Listing> listingResultList = query.getResultList();
        return new SearchDaoResponse(listingResultList, totalItems);
    }

    private List<Long> getListingIdsFromFilter(SearchRequestMapper searchRequestMapper) {
        List<Long> listingIdsFromFilter = new ArrayList<>();
        if (searchRequestMapper.getFilters() != null && !searchRequestMapper.getFilters().isEmpty()) {
            List<Long> listingFilters = listingFilterService.findByFilterOptionIds(searchRequestMapper.getFilters());
            if (!listingFilters.isEmpty()) {
                listingIdsFromFilter.addAll(listingFilters);
            }
        }
        return listingIdsFromFilter;
    }

    private List<Predicate> createPredicates(SearchCriteriaParameter searchCriteriaParameter) {
        List<Predicate> predicates = new ArrayList<>();
        if (searchCriteriaParameter.searchRequestMapper().getAreaId() != null) {
            Predicate areaPredicate = getAreaPredicate(searchCriteriaParameter);
            predicates.add(areaPredicate);
        }

        if(searchCriteriaParameter.searchRequestMapper().getPlaceId() != null) {
            Predicate placePredicate = getPlacePredicate(searchCriteriaParameter);
            predicates.add(placePredicate);
        }

        if(searchCriteriaParameter.searchRequestMapper().getBrandId() != null) {
           Predicate brandPredicate = getBrandPredicate(searchCriteriaParameter);
           predicates.add(brandPredicate);
        }

        if(searchCriteriaParameter.searchRequestMapper().getBrandModelId() != null) {
            Predicate brandModelPredicate = getBrandModelPredicate(searchCriteriaParameter);
            predicates.add(brandModelPredicate);
        }

        if(searchCriteriaParameter.searchRequestMapper().getPriceFrom() != null) {
            Predicate priceFromPredicate = getPriceFromPredicate(searchCriteriaParameter);
            predicates.add(priceFromPredicate);
        }

        if(searchCriteriaParameter.searchRequestMapper().getPriceTo() != null) {
            Predicate priceToPredicate = getPriceToPredicate(searchCriteriaParameter);
            predicates.add(priceToPredicate);
        }

        return predicates;
    }

    private Predicate getPriceToPredicate(SearchCriteriaParameter searchCriteriaParameter) {
        return searchCriteriaParameter.criteriaBuilder().lessThanOrEqualTo(
                searchCriteriaParameter.root().get(PRICE_COLUMN),
                searchCriteriaParameter.searchRequestMapper().getPriceTo()
        );
    }

    private Predicate getPriceFromPredicate(SearchCriteriaParameter searchCriteriaParameter) {
        return searchCriteriaParameter.criteriaBuilder().greaterThanOrEqualTo(
                searchCriteriaParameter.root().get(PRICE_COLUMN),
                searchCriteriaParameter.searchRequestMapper().getPriceFrom()
        );
    }

    private Predicate getBrandModelPredicate(SearchCriteriaParameter searchCriteriaParameter) {
        return searchCriteriaParameter.criteriaBuilder().equal(
                searchCriteriaParameter.root().join("brandModel").get("id"),
                searchCriteriaParameter.searchRequestMapper().getBrandModelId()
        );
    }

    private Predicate getBrandPredicate(SearchCriteriaParameter searchCriteriaParameter) {
        return searchCriteriaParameter.criteriaBuilder().equal(
                searchCriteriaParameter.root().join("brand").get("id"),
                searchCriteriaParameter.searchRequestMapper().getBrandId()
        );
    }

    private Predicate getPlacePredicate(SearchCriteriaParameter searchCriteriaParameter) {
        return searchCriteriaParameter.criteriaBuilder().equal(
                searchCriteriaParameter.root().join("place").get("id"),
                searchCriteriaParameter.searchRequestMapper().getPlaceId()
        );
    }

    private static Predicate getAreaPredicate(SearchCriteriaParameter searchCriteriaParameter) {
        return searchCriteriaParameter.criteriaBuilder().equal(
                searchCriteriaParameter.root().join("area").get("id"),
                searchCriteriaParameter.searchRequestMapper().getAreaId());
    }
}
