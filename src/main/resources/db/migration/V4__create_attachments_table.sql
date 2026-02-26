CREATE TABLE attachments (
    id BIGSERIAL PRIMARY KEY,
    link VARCHAR(500),
    key VARCHAR(500) UNIQUE NOT NULL,
    original_name VARCHAR(255),
    content_type VARCHAR(100),
    bucket_name VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);
