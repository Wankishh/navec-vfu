CREATE TABLE comments
(
    id         BIGINT AUTO_INCREMENT  NOT NULL,
    user_id    BIGINT                 NOT NULL,
    listing_id BIGINT                 NOT NULL,
    comment    VARCHAR(255)           NOT NULL,
    created_at datetime DEFAULT NOW() NOT NULL,
    updated_at datetime DEFAULT NOW() ON UPDATE NOW() NOT NULL,
    CONSTRAINT pk_comments PRIMARY KEY (id)
);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_LISTING FOREIGN KEY (listing_id) REFERENCES listings (id);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);