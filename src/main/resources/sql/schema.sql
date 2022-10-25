CREATE TABLE IF NOT EXISTS card
(
    id     BIGSERIAL PRIMARY KEY,
    number TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS event
(
    id        BIGSERIAL PRIMARY KEY,
    type      VARCHAR(64) NOT NULL,
    timestamp TIMESTAMP   NOT NULL,
    card_id   BIGSERIAL   NOT NULL,
    FOREIGN KEY (card_id) REFERENCES card (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS student
(
    id         BIGSERIAL PRIMARY KEY,
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
    phone_number VARCHAR(12)  NOT NULL,
    student_id   BIGINT DEFAULT NULL,
    FOREIGN KEY (student_id) REFERENCES student (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS notification_config
(
    id            BIGSERIAL PRIMARY KEY,
    respondent_id BIGINT NOT NULL,
    FOREIGN KEY (respondent_id) REFERENCES respondent (id) ON DELETE CASCADE
);
