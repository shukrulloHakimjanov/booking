CREATE TABLE hotels
(
    id             BIGSERIAL PRIMARY KEY,
    owner_id       BIGINT        NOT NULL,
    name           VARCHAR(255)  NOT NULL,
    description    TEXT,
    city_id        BIGINT        NOT NULL,
    address        TEXT,
    rating         DECIMAL(2, 1) NOT NULL DEFAULT 0.0,
    check_in_time  TIME          NOT NULL,
    check_out_time TIME          NOT NULL,
    created_at     TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP,
    is_active      BOOLEAN       NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_hotels_owner FOREIGN KEY (owner_id) REFERENCES users (id),
    CONSTRAINT fk_hotels_city FOREIGN KEY (city_id) REFERENCES cities (id),
    CONSTRAINT chk_rating CHECK (rating >= 0 AND rating <= 5.0)
);

CREATE TABLE hotel_attachment
(
    hotel_id      BIGINT NOT NULL,
    attachment_id BIGINT NOT NULL,

    PRIMARY KEY (hotel_id, attachment_id),
    CONSTRAINT fk_hotel_attachment_hotel FOREIGN KEY (hotel_id) REFERENCES hotels (id) ON DELETE CASCADE,
    CONSTRAINT fk_hotel_attachment_attachment FOREIGN KEY (attachment_id) REFERENCES attachments (id) ON DELETE CASCADE
);

CREATE TABLE hotel_amenities
(
    hotel_id     BIGINT NOT NULL,
    amenities_id BIGINT NOT NULL,

    PRIMARY KEY (hotel_id, amenities_id),
    CONSTRAINT fk_hotel_amenities_hotel FOREIGN KEY (hotel_id) REFERENCES hotels (id) ON DELETE CASCADE,
    CONSTRAINT fk_hotel_amenities_amenity FOREIGN KEY (amenities_id) REFERENCES amenities (id) ON DELETE CASCADE
);
