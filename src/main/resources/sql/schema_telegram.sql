CREATE TABLE IF NOT EXISTS telegram_client
(
    id      BIGSERIAL PRIMARY KEY,
    chat_id BIGINT DEFAULT NULL UNIQUE,
    verified BOOL DEFAULT FALSE,
    respondent_id BIGINT NOT NULL,
    FOREIGN KEY (respondent_id) REFERENCES respondent (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS telegram_auth_token
(
    id BIGSERIAL PRIMARY KEY,
    telegram_client_id BIGINT NOT NULL UNIQUE,
    code VARCHAR(8) NOT NULL,
    attempts INTEGER NOT NULL default 0,
    timestamp TIMESTAMP NOT NULL DEFAULT now(),
    FOREIGN KEY (telegram_client_id) REFERENCES telegram_client (id) ON DELETE CASCADE
);
