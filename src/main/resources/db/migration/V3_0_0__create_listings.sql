CREATE TABLE if not exists db.listings
(
    id            BIGINT AUTO_INCREMENT      NOT NULL,
    user_id       BIGINT                     NOT NULL,
    section_id    BIGINT                     NOT NULL,
    title         VARCHAR(255)               NULL,
    `description` TEXT                       NOT NULL,
    area_id       INT unsigned NOT NULL,
    place_id      INT unsigned NOT NULL,
    price         DECIMAL(11, 2)               NOT NULL,
    price_bg      DECIMAL(11, 2)               NOT NULL,
    price_eu      DECIMAL(11, 2)               NOT NULL,
    currency      TINYINT      DEFAULT 0     NOT NULL,
    youtube_url   VARCHAR(255)               NULL,
    watchers      INT UNSIGNED DEFAULT 0     NOT NULL,
    deleted_at    datetime     DEFAULT  NULL,
    created_at    datetime     DEFAULT NOW() NOT NULL,
    updated_at    datetime     DEFAULT NOW() ON UPDATE NOW(),
    CONSTRAINT pk_listings PRIMARY KEY (id)
);

ALTER TABLE db.listings ADD CONSTRAINT FK_LISTINGS_ON_SECTION FOREIGN KEY (section_id) REFERENCES db.sections (id);
ALTER TABLE db.listings ADD CONSTRAINT FK_LISTINGS_ON_USER FOREIGN KEY (user_id) REFERENCES db.users (id);
ALTER TABLE db.listings ADD CONSTRAINT FK_LISTINGS_ON_AREA FOREIGN KEY (area_id) REFERENCES db.areas (id);
ALTER TABLE db.listings ADD CONSTRAINT FK_LISTINGS_ON_PLACE FOREIGN KEY (place_id) REFERENCES db.places (id);