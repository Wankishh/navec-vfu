CREATE TABLE documents
(
    id         BIGINT AUTO_INCREMENT  NOT NULL,
    type       VARCHAR(255)           NULL,
    content    TEXT                   NOT NULL,
    created_at datetime DEFAULT NOW() NOT NULL,
    updated_at datetime DEFAULT NOW() ON UPDATE NOW() NOT NULL,
    CONSTRAINT pk_documents PRIMARY KEY (id)
);

CREATE INDEX searchable_type ON documents (type);


INSERT INTO documents(type, content)
    VALUES ('PrivacyPolicy', '<h1>Privacy Policy</h1>'), ('TermsOfUse', '<h1>Terms of Use</h1>')