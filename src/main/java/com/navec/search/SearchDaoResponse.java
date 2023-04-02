package com.navec.search;

import com.navec.listing.Listing;

import java.util.List;

public record SearchDaoResponse(List<Listing> data, int totalItems) {
}
