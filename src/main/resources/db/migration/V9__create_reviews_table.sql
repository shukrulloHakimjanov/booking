CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    booking_id UUID NOT NULL,
    user_id BIGINT NOT NULL,
    hotel_id BIGINT NOT NULL,
    rating INT NOT NULL,
    service_rating INT,
    comment TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    
    CONSTRAINT fk_reviews_booking FOREIGN KEY (booking_id) REFERENCES booking(id) ON DELETE CASCADE,
    CONSTRAINT fk_reviews_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_reviews_hotel FOREIGN KEY (hotel_id) REFERENCES hotels(id),
    CONSTRAINT chk_rating CHECK (rating >= 1 AND rating <= 5),
    CONSTRAINT chk_service_rating CHECK (service_rating IS NULL OR (service_rating >= 1 AND service_rating <= 5)),
    CONSTRAINT uq_booking_review UNIQUE (booking_id)
);
