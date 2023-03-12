create table db.forgotten_passwords
(
    id bigint not null AUTO_INCREMENT primary key,
    user_id  bigint not null,
    token varchar(255) not null,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULl DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Searching
create index token_search on db.forgotten_passwords (token);

