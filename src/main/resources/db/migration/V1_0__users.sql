create table db.users
(
    id bigint not null AUTO_INCREMENT primary key,
    email     varchar(255) not null,
    name varchar(255) not null,
    password  varchar(255) not null,
    active    TINYINT NOT NULL DEFAULT 0,
    role      varchar(255) not null,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULl DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Searching
create index user_email_index on db.users (email);

-- Admin123456
INSERT INTO db.users(email, name, password, active, role)
    VALUES ('admin@navec.bg', 'Admin', '$2a$12$HMgOHtkMDq0iYxJvxtAw5uiTGJ9P7j6G3CQJjz3q2bGNoIeRoqoeK', 1, 'ADMIN'),
           -- User123456
           ('user@navec.bg', 'User', '$2a$10$Ll1F306bTNkCwZhFuH2uKO0zUwgPM/LtJXsXLDnzd99wRZIGl2u02', 1, 'USER')


