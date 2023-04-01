package com.navec.brand;

import com.navec.brand_model.BrandModel;
import com.navec.listing.Listing;
import com.navec.section.Section;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "brands")
@NamedEntityGraph(
        name = "with-models",
        attributeNodes = {
                @NamedAttributeNode("models"),
                @NamedAttributeNode("section")
        }
)
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String slug;

    private Integer order;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "seo_title")
    private String seoTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private Section section;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "brand", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<BrandModel> models;

    @OneToMany(mappedBy = "brand")
    private Collection<Listing> listings;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private java.sql.Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private java.sql.Timestamp updatedAt;

}
