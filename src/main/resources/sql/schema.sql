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
    card_id    BIGINT       NOT NULL,
    FOREIGN KEY (card_id) REFERENCES card (id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS respondent
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(128) NOT NULL,
    surname      VARCHAR(128) NOT NULL,
    patronymic   VARCHAR(128) NOT NULL,
    phone_number TEXT         NOT NULL
);

CREATE TABLE IF NOT EXISTS student_respondent
(
    id            BIGSERIAL PRIMARY KEY,
    student_id    VARCHAR(8) NOT NULL,
    respondent_id BIGINT     NOT NULL,
    CONSTRAINT pk UNIQUE (student_id, respondent_id),
    FOREIGN KEY (student_id) REFERENCES student (id) ON DELETE CASCADE,
    FOREIGN KEY (respondent_id) REFERENCES respondent (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS respondent_config
(
    id BIGSERIAL PRIMARY KEY,
    respondent_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (respondent_id) REFERENCES respondent (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS skud_event
(
    id         BIGSERIAL PRIMARY KEY,
    type       TEXT       NOT NULL,
    timestamp  TIMESTAMP  NOT NULL,
    student_id VARCHAR(8) NOT NULL,
    FOREIGN KEY (student_id) REFERENCES student (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS telegram_client
(

);
