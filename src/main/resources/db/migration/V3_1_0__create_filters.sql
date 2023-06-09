CREATE TABLE filters
(
    id         BIGINT AUTO_INCREMENT     NOT NULL,
    type       VARCHAR(255) DEFAULT 'NORMAL' NOT NULL,
    input_type VARCHAR(255) DEFAULT 'SELECT' NOT NULL,
    section_id BIGINT                    NOT NULL,
    name       VARCHAR(255)              NOT NULL,
    required   TINYINT DEFAULT 1,
    show_in_preview   TINYINT DEFAULT 0,
    created_at datetime DEFAULT NOW()    NOT NULL,
    updated_at datetime DEFAULT NOW()    NOT NULL,
    CONSTRAINT pk_filter PRIMARY KEY (id)
);

# ALTER TABLE filters
#     ADD CONSTRAINT FK_FILTER_ON_SECTION FOREIGN KEY (section_id) REFERENCES db.sections (id);

INSERT INTO filters (id, type, input_type, section_id, name, required, show_in_preview)
    VALUES(1, 'NORMAL', 'RADIO', 1, 'Състояние', 1, 0),
          (2, 'INPUT', 'INPUT', 1, 'VIN', 0, 0),
          (4, 'INPUT', 'INPUT', 1, 'Модификация', 0, 0),
          (6, 'NORMAL', 'SELECT', 1, 'Купе', 1, 0),
          (7, 'NORMAL', 'SELECT', 1, 'Скоростна кутия', 1, 0),
          (8, 'INPUT', 'NUMBER', 1, 'Брой предавки', 1, 0),
          (9, 'NORMAL', 'SELECT', 1, 'Гориво', 0, 1),
          (10, 'INPUT', 'NUMBER', 1, 'Мощност', 0, 0),
          (11, 'INPUT', 'NUMBER', 1, 'Кубатура', 1, 0),
          (12, 'INPUT', 'DATE', 1, 'Година', 1, 0),
          (13, 'INPUT', 'NUMBER', 1, 'Пробег', 1, 1),
          (14, 'NORMAL', 'SELECT', 1, 'Брой Врати', 1, 0),
          (15, 'NORMAL', 'SELECT', 1, 'Цвят', 1, 0),
          (16, 'NORMAL', 'CHECKBOX', 1, 'Металик', 0, 0),
          (17, 'NORMAL', 'SELECT', 1, 'Вид тапицерия', 0, 0),
          (18, 'NORMAL', 'SELECT', 1, 'Цвят на интериора', 0, 0),
          (19, 'INPUT', 'NUMBER', 1, 'Брой предишни собственици', 0, 0),
          (20, 'INPUT', 'NUMBER', 1, 'Брой цилиндри', 0, 0),
          (21, 'NORMAL', 'SELECT', 1, 'Евростандарт', 1, 0),
          (22, 'NORMAL', 'SELECT', 1, 'Задвижване', 0, 0),
          (23, 'NORMAL', 'SELECT', 1, 'Къде се намира автомобила в момента ?', 1, 0);
