CREATE TABLE IF NOT EXISTS card
(
    id     BIGSERIAL PRIMARY KEY,
    number TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS skud_event
(
    id        BIGSERIAL PRIMARY KEY,
    type      VARCHAR(64) NOT NULL,
    card_id   BIGSERIAL   NOT NULL,
    timestamp TIMESTAMP   NOT NULL,
    FOREIGN KEY (card_id) REFERENCES card (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS student
(
    id         VARCHAR(8) PRIMARY KEY,
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
    student_id   VARCHAR(8) DEFAULT NULL,
    FOREIGN KEY (student_id) REFERENCES student (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS notification_config
(
    id            BIGSERIAL PRIMARY KEY,
    respondent_id BIGINT NOT NULL,
    FOREIGN KEY (respondent_id) REFERENCES respondent (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS sms
(
    id            BIGSERIAL PRIMARY KEY,
    payload       TEXT        NOT NULL,
    status        VARCHAR(64) NOT NULL DEFAULT 'WAITING',
    high_priority BOOL        NOT NULL DEFAULT FALSE,
    respondent_id BIGINT      NOT NULL,
    created_at    TIMESTAMP   NOT NULL DEFAULT now(),
    sent_at       TIMESTAMP            DEFAULT NULL,
    FOREIGN KEY (respondent_id) REFERENCES respondent (id) ON DELETE CASCADE
);
