CREATE TABLE IF NOT EXISTS telegram_client
(
    id            BIGSERIAL PRIMARY KEY,
    chat          BIGINT DEFAULT NULL UNIQUE,
    verified      BOOL   DEFAULT FALSE,
    respondent_id BIGINT NOT NULL UNIQUE, -- unique?
    FOREIGN KEY (respondent_id) REFERENCES respondent (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS telegram_notification
(
    id                 BIGSERIAL PRIMARY KEY,
    type               TEXT   NOT NULL,
    payload            JSONB  NOT NULL,
    telegram_client_id BIGINT NOT NULL,
    FOREIGN KEY (telegram_client_id) REFERENCES telegram_client (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS telegram_config
(
    id                 BIGSERIAL PRIMARY KEY,
    telegram_client_id BIGINT NOT NULL UNIQUE,
    skud_enabled       BOOL   NOT NULL DEFAULT TRUE,
    FOREIGN KEY (telegram_client_id) REFERENCES telegram_client (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS telegram_auth_token
(
    id                 BIGSERIAL PRIMARY KEY,
    telegram_client_id BIGINT    NOT NULL UNIQUE,
    code               TEXT      NOT NULL,
    attempts           INTEGER   NOT NULL default 0,
    timestamp          TIMESTAMP NOT NULL DEFAULT now(),
    FOREIGN KEY (telegram_client_id) REFERENCES telegram_client (id) ON DELETE CASCADE
);
