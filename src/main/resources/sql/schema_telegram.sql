CREATE TABLE IF NOT EXISTS telegram_client
(
    id                   BIGSERIAL PRIMARY KEY,
    chat                 BIGINT DEFAULT NULL UNIQUE,
    respondent_config_id BIGINT NOT NULL,
    FOREIGN KEY (respondent_config_id) REFERENCES respondent_config (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS telegram_notification
(
    id                 BIGSERIAL PRIMARY KEY,
    type               TEXT   NOT NULL,
    payload            JSONB  NOT NULL,
    telegram_client_id BIGINT NOT NULL,
    FOREIGN KEY (telegram_client_id) REFERENCES telegram_client (id) ON DELETE CASCADE
);
