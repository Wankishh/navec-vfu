package com.navec.listing_filter;

import com.navec.filter.Filter;
import com.navec.filter.option.FilterOption;
import com.navec.listing.Listing;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "listing_filters")
@NamedEntityGraph(
        name = "listing-filter",
        attributeNodes = {
                @NamedAttributeNode(value = "filter"),
                @NamedAttributeNode(value = "filterOption"),
        }
)
public class ListingFilter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id")
    private Listing listing;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_id", referencedColumnName = "id")
    private Filter filter;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_option_id", referencedColumnName = "id")
    private FilterOption filterOption;

    private String value;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private java.sql.Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private java.sql.Timestamp updatedAt;
}
