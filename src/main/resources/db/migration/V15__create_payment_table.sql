CREATE TABLE payments
(
    id             BIGSERIAL PRIMARY KEY,
    booking_id     BIGINT         NOT NULL,
    amount         NUMERIC(10, 2) NOT NULL,
    currency       VARCHAR(3)     NOT NULL,
    payment_method VARCHAR(30)    NOT NULL,
    status         VARCHAR(20)    NOT NULL DEFAULT 'PENDING',
    transaction_id VARCHAR(255) UNIQUE,
    booking_uuid   VARCHAR(255),
    created_at     TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_active      BOOLEAN        NOT NULL DEFAULT TRUE
);