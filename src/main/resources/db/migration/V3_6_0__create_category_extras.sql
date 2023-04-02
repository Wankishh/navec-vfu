CREATE TABLE category_extras
(
    id         BIGINT AUTO_INCREMENT  NOT NULL,
    section_id BIGINT                 NULL,
    name       VARCHAR(255)           NULL,
    name_en    VARCHAR(255)           NOT NULL,
    created_at datetime DEFAULT NOW() NOT NULL,
    updated_at datetime DEFAULT NOW() ON UPDATE NOW() NOT NULL,
    CONSTRAINT pk_category_extras PRIMARY KEY (id)
);

ALTER TABLE category_extras
    ADD CONSTRAINT FK_CATEGORY_EXTRAS_ON_SECTION FOREIGN KEY (section_id) REFERENCES sections (id);


INSERT INTO category_extras (id, section_id, name, name_en)
    VALUES (1, 1, 'Екстериор', ''),
           (2, 1, 'Комфорт', ''),
           (3, 1, 'Сигурност', ''),
           (4, 1, 'Специфични', '');
