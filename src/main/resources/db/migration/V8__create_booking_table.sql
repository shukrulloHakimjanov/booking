CREATE TABLE booking
(
    id               UUID PRIMARY KEY,
    user_id          BIGINT NOT NULL,
    hotel_id         BIGINT NOT NULL,
    room_id          BIGINT NOT NULL,
    check_in_date    DATE NOT NULL,
    check_out_date   DATE NOT NULL,
    num_guests       INTEGER NOT NULL,
    total_price      NUMERIC(10, 2) NOT NULL,
    status           VARCHAR(40) NOT NULL,
    special_requests TEXT,
    payment_id       VARCHAR(255),
    guests           JSONB NOT NULL ,
    expire_date      DATE,
    created_at       TIMESTAMP,
    updated_at       TIMESTAMP,
    is_active        BOOLEAN DEFAULT TRUE,

    CONSTRAINT fk_booking_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_booking_hotel FOREIGN KEY (hotel_id) REFERENCES hotels (id) ON DELETE CASCADE,
    CONSTRAINT fk_booking_room FOREIGN KEY (room_id) REFERENCES rooms (id) ON DELETE CASCADE
);