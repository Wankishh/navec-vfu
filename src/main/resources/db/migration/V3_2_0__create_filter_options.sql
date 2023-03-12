CREATE TABLE IF NOT EXISTS filter_options
(
    id         BIGINT AUTO_INCREMENT  NOT NULL,
    filter_id  BIGINT                 NOT NULL,
    name       VARCHAR(255)           NOT NULL,
    created_at datetime DEFAULT NOW(),
    updated_at datetime DEFAULT NOW() ON UPDATE NOW(),
    CONSTRAINT pk_filter_options PRIMARY KEY (id)
);

# ALTER TABLE filter_options
#     ADD CONSTRAINT FK_FILTER_OPTIONS_ON_FILTER FOREIGN KEY (filter_id) REFERENCES filters (id);


INSERT INTO filter_options (filter_id, name)
    VALUES
    (1, 'Употребяван'),
    (1, 'С проблеми или ударен'),
    (1, 'На части');

INSERT INTO filter_options (filter_id, name)
VALUES
    (6, 'Седан'),
    (6, 'Хечбек'),
    (6, 'Комби'),
    (6, 'Купе'),
    (6, 'Кабрио'),
    (6, 'Джип'),
    (6, 'Пикап'),
    (6, 'Ван'),
    (6, 'Стреч лимузина');

INSERT INTO filter_options (filter_id, name)
VALUES
    (7, 'Ръчна'),
    (7, 'Автоматична'),
    (7, 'Полуавтоматична');


# Бензин, Дизел, Газ/Бензин, Метан/Бензин, Хибрид (Ел/Бензин), Хибрид (Ел/Дизел), Водород, Етанол, Електричество

INSERT INTO filter_options (filter_id, name)
VALUES
    (9, 'Бензин'),
    (9, 'Дизел'),
    (9, 'Газ/Бензин'),
    (9, 'Метан/Бензин'),
    (9, 'Хибрид (Ел/Бензин)'),
    (9, 'Хибрид (Ел/Дизел)'),
    (9, 'Водород'),
    (9, 'Етанол'),
    (9, 'Електричество');

INSERT INTO filter_options (filter_id, name)
VALUES
    (14, '2/3'),
    (14, '4/5'),
    (14, '6/7');

INSERT INTO filter_options (filter_id, name)
VALUES
    (15, 'Бежов'),
    (15, 'Бордо'),
    (15, 'Бронзов'),
    (15, 'Бял'),
    (15, 'Виолетов'),
    (15, 'Жълт'),
    (15, 'Зелен'),
    (15, 'Златен'),
    (15, 'Кафяв'),
    (15, 'Лилав'),
    (15, 'Оранжев'),
    (15, 'Охра'),
    (15, 'Перла'),
    (15, 'Розов'),
    (15, 'Светло зелен'),
    (15, 'Светло сив'),
    (15, 'Светло син'),
    (15, 'Сив'),
    (15, 'Син'),
    (15, 'Сребърен'),
    (15, 'Тъмно зелен'),
    (15, 'Тъмно сив'),
    (15, 'Тъмно син'),
    (15, 'Хамелеон'),
    (15, 'Червен'),
    (15, 'Черен');
# Кожа, Кожа/Велур, Велур, Алкантара, Плат, Друг

INSERT INTO filter_options (filter_id, name)
VALUES
    (17, 'Кожа'),
    (17, 'Кожа/Велур'),
    (17, 'Велур'),
    (17, 'Алкантара'),
    (17, 'Плат'),
    (17, 'Друг');

# бежов, бял, сив, черен,червен, кафяв, син, червен, жълт, зелен, оранжев, друг

INSERT INTO filter_options (filter_id, name)
VALUES
    (18, 'бежов'),
    (18, 'бял'),
    (18, 'сив'),
    (18, 'черен'),
    (18, 'червен'),
    (18, 'кафяв'),
    (18, 'син'),
    (18, 'жълт'),
    (18, 'зелен'),
    (18, 'оранжев'),
    (18, 'друг');

INSERT INTO filter_options (filter_id, name)
VALUES
    (21, 'Euro 1'),
    (21, 'Euro 2'),
    (21, 'Euro 3'),
    (21, 'Euro 4'),
    (21, 'Euro 5'),
    (21, 'Euro 6'),
    (21, 'Euro 6c'),
    (21, 'Euro 6d'),
    (21, 'Euro 6d-TEMP');


# Предно предаване, Задно предаване, 4Х4

INSERT INTO filter_options (filter_id, name)
VALUES
    (22, 'Предно предаване'),
    (22, 'Задно предаване'),
    (22, '4Х4');

INSERT INTO filter_options (filter_id, name)
VALUES
    (23, 'В България'),
    (23, 'Извън България');