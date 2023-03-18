CREATE TABLE contacts
(
    id         BIGINT AUTO_INCREMENT  NOT NULL,
    name       VARCHAR(255)           NOT NULL,
    email      VARCHAR(255)           NOT NULL,
    phone      VARCHAR(255)           NOT NULL,
    message    VARCHAR(255)           NOT NULL,
    created_at datetime DEFAULT NOW() NOT NULL,
    updated_at datetime DEFAULT NOW() ON UPDATE NOW() NOT NULL,
    CONSTRAINT pk_contacts PRIMARY KEY (id)
);