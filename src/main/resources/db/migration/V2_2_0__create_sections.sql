CREATE TABLE IF NOT EXISTS db.sections
(
    id         BIGINT AUTO_INCREMENT  NOT NULL,
    name       VARCHAR(255)           NULL,
    seo_title  VARCHAR(255)           NULL,
    images     tinyint  DEFAULT 10,
    slug       VARCHAR(255)           NULL,
    image_url      VARCHAR(255)           NULL,
    created_at datetime DEFAULT NOW() NOT NULL,
    updated_at datetime DEFAULT NOW() ON UPDATE NOW(),
    CONSTRAINT pk_sections PRIMARY KEY (id)
);


INSERT INTO db.sections (id, name, slug, seo_title, images)
VALUES (1, 'Автомобили', 'avtomobili', 'Автомобили', 15),
       (2, 'Бусове', 'busove', 'Бусове', 14),
       (3, 'Мотори', 'motori', 'Мотори', 14),
       (4, 'Каравани и кемпери', 'karavani-i-kemperi', 'Каравани и кемпери', 14),
       (5, 'Камиони', 'kamioni', 'Камиони', 14),
       (6, 'Яхти и лодки', 'qhti-i-lodki', 'Яхти и лодки', 14),
       (7, 'Строителна техника', 'stroitelna-tehnika', 'Строителна техника', 14),
       (8, 'Селскостопанска техника', 'selskostopanska-tehnika', 'Селскостопанска техника', 14),
       (9, 'Ремаркета', 'remarketa', 'Ремаркета', 10),
       (10, 'Части', 'chasti', 'Части', 0),
       (11, 'Гуми и джанти', 'gumi-i-dvanti', 'Гуми и джанти', 0),
       (12, 'Аксесоари и консумативи', 'aksesoari-i-konsumativi', 'Аксесоари и консумативи', 0);