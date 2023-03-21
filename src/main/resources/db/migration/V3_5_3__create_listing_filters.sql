CREATE TABLE listing_filters
(
    id               BIGINT AUTO_INCREMENT  NOT NULL,
    listing_id       BIGINT                 NOT NULL,
    filter_id        BIGINT                 NOT NULL,
    filter_option_id BIGINT                 NULL,
    value            VARCHAR(255)           NULL,
    created_at       datetime DEFAULT NOW() NOT NULL,
    updated_at       datetime DEFAULT NOW() ON UPDATE NOW() NOT NULL,
    CONSTRAINT pk_listing_filters PRIMARY KEY (id)
);

ALTER TABLE listing_filters
    ADD CONSTRAINT FK_LISTING_FILTERS_ON_FILTER FOREIGN KEY (filter_id) REFERENCES filters (id);

ALTER TABLE listing_filters
    ADD CONSTRAINT FK_LISTING_FILTERS_ON_FILTER_OPTION FOREIGN KEY (filter_option_id) REFERENCES filter_options (id);

ALTER TABLE listing_filters
    ADD CONSTRAINT FK_LISTING_FILTERS_ON_LISTING FOREIGN KEY (listing_id) REFERENCES listings (id);