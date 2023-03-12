CREATE TABLE IF NOT EXISTS db.brand_model_series
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255)          NOT NULL,
    type       VARCHAR(255)          NOT NULL,
    created_at datetime DEFAULT NOW(),
    updated_at datetime DEFAULT NOW() ON UPDATE NOW(),
    CONSTRAINT pk_brand_model_series PRIMARY KEY (id)
);

INSERT INTO db.brand_model_series (id, name, `type`)
VALUES (1, '1 - серия', 'BMW серия'),
       (2, '2 - среия', 'Серия'),
       (3, '3 - серия', '3 Серия'),
       (4, '4 - серия', '4- серия'),
       (5, '5 - серия', '5 серия'),
       (6, '6 - серия', '6 серия'),
       (7, '7 - серия', '7 серия'),
       (8, 'M', 'Серия'),
       (9, 'X- серия', 'Х серия'),
       (10, 'Z- серия', 'Z серия');
INSERT INTO db.brand_model_series (id, name, `type`)
VALUES (11, 'IS- серия', 'Серия'),
       (12, 'A- класа', 'А класа'),
       (13, 'B- класа', 'Класа'),
       (14, 'C- класа', 'Класа'),
       (15, 'CE- класа', 'класа'),
       (16, 'CL- класа', 'Класа'),
       (17, 'CLC- класа', 'CLC- класа'),
       (18, 'CLK- класа', 'класа'),
       (19, 'CLS- класа', 'CLS- класа'),
       (20, 'E-класа', 'E-класа');
INSERT INTO db.brand_model_series (id, name, `type`)
VALUES (21, 'G- класа', 'G- класа'),
       (22, 'GL- класа', 'GL- класа'),
       (23, 'GLK- класа', 'GLK- класа'),
       (24, 'ML- класа', 'ML- класа'),
       (25, 'R- класа', 'R- класа'),
       (26, 'S-класа', 'S-класа'),
       (27, 'SL- класа', 'SL- класа'),
       (28, 'SLK- класа', 'SLK- класа'),
       (29, 'Sprinter', 'Sprinter'),
       (30, '8 - серия', '8 - серия');
INSERT INTO db.brand_model_series (id, name, `type`)
VALUES (31, 'GLA', 'GLA');