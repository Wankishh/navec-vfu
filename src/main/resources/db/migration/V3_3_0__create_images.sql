CREATE TABLE db.images
(
    id            BIGINT AUTO_INCREMENT  NOT NULL,
    listing_id    BIGINT                 NULL,
    name          VARCHAR(255)           NOT NULL,
    original_name VARCHAR(255)           NOT NULL,
    created_at    datetime DEFAULT NOW() NOT NULL,
    updated_at    datetime DEFAULT NOW() ON UPDATE now(),
    CONSTRAINT pk_images PRIMARY KEY (id)
);

ALTER TABLE db.images
    ADD CONSTRAINT FK_IMAGES_ON_LISTING FOREIGN KEY (listing_id) REFERENCES db.listings (id);