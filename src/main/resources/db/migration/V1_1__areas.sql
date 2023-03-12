CREATE TABLE db.`areas` (
     `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
     `name` varchar(255) NOT NULL,
     `slug` varchar(255) NOT NULL,
     `longtitude` decimal(11,7) DEFAULT NULL,
     `latitude` decimal(11,7) DEFAULT NULL,
     `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
     `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     PRIMARY KEY (`id`),
     KEY `idx_slug` (`slug`)
) ENGINE=InnoDB AUTO_INCREMENT=28;

INSERT INTO db.areas (id, name, slug, longtitude, latitude)
VALUES (1, 'Благоевград', 'blagoevgrad', NULL, NULL),
       (2, 'Бургас', 'burgas', NULL, NULL),
       (3, 'Варна', 'varna', NULL, NULL),
       (4, 'Велико Търново', 'veliko-turnovo', NULL, NULL),
       (5, 'Видин', 'vidin', NULL, NULL),
       (6, 'Враца', 'vraca', NULL, NULL),
       (7, 'Габрово', 'gabrovo', NULL, NULL),
       (8, 'Добрич', 'dobrich', NULL, NULL),
       (9, 'Кърджали', 'kurdjali', NULL, NULL),
       (10, 'Кюстендил', 'kustendil', NULL, NULL);
INSERT INTO db.areas (id, name, slug, longtitude, latitude)
VALUES (11, 'Ловеч', 'lovech', NULL, NULL),
       (12, 'Монтана', 'montana', NULL, NULL),
       (13, 'Пазарджик', 'pazardjik', NULL, NULL),
       (14, 'Перник', 'pernik', NULL, NULL),
       (15, 'Плевен', 'pleven', NULL, NULL),
       (16, 'Пловдив', 'plovdiv', NULL, NULL),
       (17, 'Разград', 'razgrad', NULL, NULL),
       (18, 'Русе', 'ruse', NULL, NULL),
       (19, 'Силистра', 'silistra', NULL, NULL),
       (20, 'Сливен', 'sliven', NULL, NULL);
INSERT INTO db.areas (id, name, slug, longtitude, latitude)
VALUES (21, 'Смолян', 'smolqn', NULL, NULL),
       (22, 'София', 'sofiq', NULL, NULL),
       (23, 'Стара Загора', 'stara-zagora', NULL, NULL),
       (24, 'Търговище', 'turgovishte', NULL, NULL),
       (25, 'Хасково', 'haskovo', NULL, NULL),
       (26, 'Шумен', 'shumen', NULL, NULL),
       (27, 'Ямбол', 'qmbol', NULL, NULL);