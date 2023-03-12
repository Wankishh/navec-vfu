create table db.profile
(
    id         bigint auto_increment primary key,
    user_id    bigint not null,
    name       varchar(255) not null,
    phone      varchar(255) null,
    area_id    int null,
    place_id   int null,
    longtitude decimal(11,9) NULL,
    latitude decimal(11,9) NULL,
    created_at datetime default CURRENT_TIMESTAMP null,
    updated_at datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    constraint user_id_fk
        foreign key (user_id) references db.users (id)
);

INSERT INTO profile (user_id, name) VALUES (1, 'Admin');

