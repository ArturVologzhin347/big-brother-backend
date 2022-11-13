CREATE TABLE IF NOT EXISTS card
(
    id     BIGSERIAL PRIMARY KEY,
    number TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS student
(
    id         VARCHAR(8) PRIMARY KEY UNIQUE,
    name       VARCHAR(128) NOT NULL,
    surname    VARCHAR(128) NOT NULL,
    patronymic VARCHAR(128) NOT NULL,
    card_id    BIGINT DEFAULT NULL,
    FOREIGN KEY (card_id) REFERENCES card (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS respondent
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(128) NOT NULL,
    surname      VARCHAR(128) NOT NULL,
    patronymic   VARCHAR(128) NOT NULL,
    phone_number TEXT         NOT NULL,
    student_id   VARCHAR(8) DEFAULT NULL UNIQUE,
    FOREIGN KEY (student_id) REFERENCES student (id) ON DELETE SET NULL
);


