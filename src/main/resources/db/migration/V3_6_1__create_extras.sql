CREATE TABLE extras
(
    id                BIGINT AUTO_INCREMENT                  NOT NULL,
    category_extra_id BIGINT                                 NULL,
    name              VARCHAR(255)                           NULL,
    name_en           VARCHAR(255)                           NULL,
    created_at        datetime DEFAULT NOW()                 NOT NULL,
    updated_at        datetime DEFAULT NOW() ON UPDATE NOW() NOT NULL,
    CONSTRAINT pk_extras PRIMARY KEY (id)
);

ALTER TABLE extras
    ADD CONSTRAINT FK_EXTRAS_ON_CATEGORY_EXTRA FOREIGN KEY (category_extra_id) REFERENCES category_extras (id);

INSERT INTO extras (category_extra_id, name, name_en)
VALUES (1, 'Алуминиеви джанти', ''),
       (1, 'Багажник за ски', ''),
       (1, 'Биксенонови фарове', ''),
       (1, 'Всесонни гуми', ''),
       (1, 'Дневни светлини', ''),
       (1, 'Дълги светлини без заслепяване', ''),
       (1, 'Зимни гуми', ''),
       (1, 'Ксенонови фарове', ''),
       (1, 'Лазерни светлини', ''),
       (1, 'Лети джанти', ''),
       (1, 'Летни гуми', ''),
       (1, 'Панорамен люк', ''),
       (1, 'Плъзгаща врата', ''),
       (1, 'Релси на покрива', ''),
       (1, 'Светлини за завиване', ''),
       (1, 'Спортно окачване', ''),
       (1, 'Спойлери', ''),
       (1, 'Стоманени джанти', ''),
       (1, 'Тонирани стъкла', ''),
       (1, 'Теглич', ''),
       (1, 'Халогенни фарове', ''),
       (1, 'Шибедах', ''),
       (1, 'LED фарове', '');

INSERT INTO extras (category_extra_id, name, name_en)
VALUES (2, '360° камера', ''),
       (2, 'Бордови компютър', ''),
       (2, 'Асистент за нощно виждане', ''),
       (2, 'Амбиентно осветление', ''),
       (2, 'Високоговорител', ''),
       (2, 'Въздушно окачване - Airmatic', ''),
       (2, 'Гласов контрол', ''),
       (2, 'Дистанционно палене', ''),
       (2, 'Дигитално табло за управление', ''),
       (2, 'Ел. багажник', ''),
       (2, 'Ел. регулиране на седалката', ''),
       (2, 'Ел. регулиране на задна седалка', ''),
       (2, 'Ел.стъкла', ''),
       (2, 'Ел. странично огледало', ''),
       (2, 'Климатик', ''),
       (2, 'Климатроник', ''),
       (2, '2-зонов климатроник', ''),
       (2, '3-зонен климатроник', ''),
       (2, '4-зонен климатроник', ''),
       (2, 'Кожен волан', ''),
       (2, 'Лумбална опора', ''),
       (2, 'Масажиращи седалки', ''),
       (2, 'Мултифункционален волан', ''),
       (2, 'Навигационна система', ''),
       (2, 'Обдухване', ''),
       (2, 'Отопление на предните седалките', ''),
       (2, 'Отопление на задните седалки', ''),
       (2, 'Памет на седалките', ''),
       (2, 'Печка', ''),
       (2, 'Подлакътник', ''),
       (2, 'Подгряване на волана', ''),
       (2, 'Подгряване на предното стъкло', ''),
       (2, 'Преграда на багажното отделение', ''),
       (2, 'Предупреждение за умора', ''),
       (2, 'Спортни седалки', ''),
       (2, 'Стерео уредба', ''),
       (2, 'Android Auto', ''),
       (2, 'Apple CarPlay', ''),
       (2, 'Bluetooth', ''),
       (2, 'CD плейър', ''),
       (2, 'DVD/TV', ''),
       (2, 'Head-Up дисплей', ''),
       (2, 'Isofix', ''),
       (2, 'Soft close - Вакуум на вратите', ''),
       (2, 'Steptronic, Tiptronic', ''),
       (2, 'Touchscreen', ''),
       (2, 'TV', ''),
       (2, 'USB', ''),
       (2, 'Wifi Hotspot', '');

INSERT INTO extras (category_extra_id, name, name_en)
VALUES (3, 'Автокаско', ''),
(3, 'Автопилот', ''),
(3, 'Аларма', ''),
(3, 'Асистент за дълги светлини', ''),
(3, 'Асистент за мъртва точка', ''),
(3, 'Асистент за аварийно спиране', ''),
(3, 'Асистен за изкачване по наклон', ''),
(3, 'Брониран', ''),
(3, 'Безключово палене', ''),
(3, 'Въздушни възглавници - Задни', ''),
(3, 'Въздушни възглавници - Предни', ''),
(3, 'Въздушни възглавници - Странични', ''),
(3, 'Задни сензори за паркиране', ''),
(3, 'Застраховка', ''),
(3, 'Имобилайзер', ''),
(3, 'Контрол на налягането в гумите', ''),
(3, 'Парктроник', ''),
(3, 'Почистване на фарове', ''),
(3, 'Предупреждение за напускане на лентата', ''),
(3, 'Предни сензори за паркиране', ''),
(3, 'Разпознаване на пътни знаци', ''),
(3, 'Резервна гума', ''),
(3, 'Сензор за дъжд', ''),
(3, 'Серво управление', ''),
(3, 'Старт-Стоп система', ''),
(3, 'Система за нощно виждане', ''),
(3, 'Система за ограничаване на скоростта', ''),
(3, 'Система за динамична устойчивост', ''),
(3, 'Система за защита от пробуксуване', ''),
(3, 'Система за изсушаване на накладките', ''),
(3, 'Система за контрол на спускането', ''),
(3, 'Система за подпомагане на спирането', ''),
(3, 'Tемпомат', ''),
(3, 'Центр. заключване', ''),
(3, 'Центр. заключване без ключ', ''),
(3, 'ABS', ''),
(3, 'ESP', ''),
(3, 'Airbag', ''),
(3, 'Distronic', ''),
(3, 'ESP', ''),
(3, 'GPS система за проследяване', ''),
(3, 'Keyless-Go', '');

INSERT INTO extras (category_extra_id, name, name_en)
VALUES (4, 'Гаранция', ''),
(4, 'Десен волан', ''),
(4, 'Дълга база', ''),
(4, 'Катализатор', ''),
(4, 'Къса база', ''),
(4, 'Лебедка', ''),
(4, 'Напълно обслужен', ''),
(4, 'Пригоден за инвалиди', ''),
(4, 'Ретро', ''),
(4, 'С регистрация', ''),
(4, 'Сервизна книжка', ''),
(4, 'Товарен', ''),
(4, 'Тунинг', ''),
(4, 'Учебен', ''),
(4, 'Хладилен', ''),
(4, 'OFFROAD пакет', ''),
(4, 'TAXI', '');