package com.navec.extras;

import com.navec.section.Section;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "category_extras")
@Getter
@Setter
@NamedEntityGraph(
        name = "with-extras",
        attributeNodes = {
                @NamedAttributeNode("extras")
        }
)
public class CategoryExtra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;
    private String name;
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryExtra", orphanRemoval = true)
    private List<Extra> extras;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private java.sql.Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private java.sql.Timestamp updatedAt;
}
