CREATE TABLE room_types
(
    id                BIGSERIAL PRIMARY KEY,
    hotel_id          BIGINT       NOT NULL,
    name              VARCHAR(100) NOT NULL,
    description       TEXT,
    max_guests        INT          NOT NULL,
    bed_configuration TEXT,
    created_at        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP,
    is_active         BOOLEAN      NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_room_types_hotel FOREIGN KEY (hotel_id) REFERENCES hotels (id) ON DELETE CASCADE,
    CONSTRAINT chk_max_guests CHECK (max_guests > 0)
);

CREATE TABLE room_type_amenties
(
    room_id      BIGINT NOT NULL,
    amenities_id BIGINT NOT NULL,

    PRIMARY KEY (room_id, amenities_id),
    CONSTRAINT fk_room_type_amenities_room FOREIGN KEY (room_id) REFERENCES room_types (id) ON DELETE CASCADE,
    CONSTRAINT fk_room_type_amenities_amenity FOREIGN KEY (amenities_id) REFERENCES amenities (id) ON DELETE CASCADE
);