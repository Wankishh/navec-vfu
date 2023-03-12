package com.navec.listing.brand;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@Table(name = "brand_model_series")
public class BrandModelSerie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    @OneToMany(mappedBy = "serie", fetch = FetchType.LAZY)
    private List<BrandModel> brandModels;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT NOW()")
    private java.sql.Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT NOW() ON UPDATE NOW()")
    private java.sql.Timestamp updatedAt;
}
