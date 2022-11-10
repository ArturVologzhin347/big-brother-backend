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
