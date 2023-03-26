package com.navec.filter;


import com.navec.filter.option.FilterOption;
import com.navec.section.Section;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@Table(name = "filters")
@NamedEntityGraph(
        name = "filter-with-options",
        attributeNodes = {
                @NamedAttributeNode(value = "filterOptions"),
        }
)
public class Filter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'NORMAL'", nullable = false)
    private FilterType type;
    @Column(name = "input_type", columnDefinition = "VARCHAR(255) DEFAULT 'SELECT'", nullable = false)
    @Enumerated(EnumType.STRING)
    private InputType inputType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TINYINT DEFAULT 0")
    private Boolean required;

    @Column(name = "show_in_preview", columnDefinition = "TINYINT DEFAULT 0")
    private Boolean showInPreview;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filter", orphanRemoval = true)
    private List<FilterOption> filterOptions;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private java.sql.Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", nullable = false)
    private java.sql.Timestamp updatedAt;
}
