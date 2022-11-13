CREATE TABLE IF NOT EXISTS skud_event
(
    id        BIGSERIAL PRIMARY KEY,
    type      VARCHAR(64) NOT NULL,
    card_id   BIGSERIAL   NOT NULL,
    timestamp TIMESTAMP   NOT NULL,
    FOREIGN KEY (card_id) REFERENCES card (id) ON DELETE CASCADE
);
