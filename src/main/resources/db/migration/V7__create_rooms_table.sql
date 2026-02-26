CREATE TABLE rooms (
    id BIGSERIAL PRIMARY KEY,
    room_type_id BIGINT NOT NULL,
    room_number VARCHAR(50),
    floor INT,
    accommodation_type VARCHAR(50),
    status VARCHAR(50) NOT NULL DEFAULT 'AVAILABLE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    
    CONSTRAINT fk_rooms_room_type FOREIGN KEY (room_type_id) REFERENCES room_types(id) ON DELETE CASCADE
);
