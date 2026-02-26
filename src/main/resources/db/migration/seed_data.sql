-- Seed Data for Hotel Booking System
-- Description: Sample data for development and testing

-- Insert Sample Cities
INSERT INTO cities (name, country, created_at, is_active) VALUES
('New York', 'United States', CURRENT_TIMESTAMP, true),
('London', 'United Kingdom', CURRENT_TIMESTAMP, true),
('Paris', 'France', CURRENT_TIMESTAMP, true),
('Tokyo', 'Japan', CURRENT_TIMESTAMP, true),
('Dubai', 'United Arab Emirates', CURRENT_TIMESTAMP, true),
('Singapore', 'Singapore', CURRENT_TIMESTAMP, true),
('Barcelona', 'Spain', CURRENT_TIMESTAMP, true),
('Rome', 'Italy', CURRENT_TIMESTAMP, true);

-- Insert Sample Amenities
INSERT INTO amenities (name, category, created_at, is_active) VALUES
-- Hotel Amenities
('Free WiFi', 'Connectivity', CURRENT_TIMESTAMP, true),
('Swimming Pool', 'Recreation', CURRENT_TIMESTAMP, true),
('Fitness Center', 'Recreation', CURRENT_TIMESTAMP, true),
('Spa', 'Wellness', CURRENT_TIMESTAMP, true),
('Restaurant', 'Dining', CURRENT_TIMESTAMP, true),
('Bar', 'Dining', CURRENT_TIMESTAMP, true),
('Room Service', 'Service', CURRENT_TIMESTAMP, true),
('24/7 Reception', 'Service', CURRENT_TIMESTAMP, true),
('Parking', 'Facilities', CURRENT_TIMESTAMP, true),
('Airport Shuttle', 'Transportation', CURRENT_TIMESTAMP, true),
('Business Center', 'Business', CURRENT_TIMESTAMP, true),
('Conference Rooms', 'Business', CURRENT_TIMESTAMP, true),
('Laundry Service', 'Service', CURRENT_TIMESTAMP, true),
('Concierge', 'Service', CURRENT_TIMESTAMP, true),
('Pet Friendly', 'Facilities', CURRENT_TIMESTAMP, true),

-- Room Amenities
('Air Conditioning', 'Climate', CURRENT_TIMESTAMP, true),
('Heating', 'Climate', CURRENT_TIMESTAMP, true),
('TV', 'Entertainment', CURRENT_TIMESTAMP, true),
('Minibar', 'Refreshments', CURRENT_TIMESTAMP, true),
('Coffee Maker', 'Refreshments', CURRENT_TIMESTAMP, true),
('Safe', 'Security', CURRENT_TIMESTAMP, true),
('Hair Dryer', 'Bathroom', CURRENT_TIMESTAMP, true),
('Bathtub', 'Bathroom', CURRENT_TIMESTAMP, true),
('Shower', 'Bathroom', CURRENT_TIMESTAMP, true),
('Balcony', 'Room Features', CURRENT_TIMESTAMP, true),
('City View', 'View', CURRENT_TIMESTAMP, true),
('Ocean View', 'View', CURRENT_TIMESTAMP, true),
('Desk', 'Furniture', CURRENT_TIMESTAMP, true),
('Wardrobe', 'Furniture', CURRENT_TIMESTAMP, true),
('Soundproofing', 'Comfort', CURRENT_TIMESTAMP, true);

-- Insert Sample Users
-- Password hash is for 'password123' (use proper hashing in production)
INSERT INTO users (email, password_hash, first_name, last_name, phone, role, status, created_at, is_active) VALUES
('admin@hotel.com', '$2a$10$X5wFWMLqIqY3qRh1qmGpfOYg0ZNNXqnFvMQNJoVVEQeNkVJvj7rXW', 'Admin', 'User', '+1234567890', 'ADMIN', 'ACTIVE', CURRENT_TIMESTAMP, true),
('owner1@hotel.com', '$2a$10$X5wFWMLqIqY3qRh1qmGpfOYg0ZNNXqnFvMQNJoVVEQeNkVJvj7rXW', 'John', 'Smith', '+1234567891', 'OWNER', 'ACTIVE', CURRENT_TIMESTAMP, true),
('owner2@hotel.com', '$2a$10$X5wFWMLqIqY3qRh1qmGpfOYg0ZNNXqnFvMQNJoVVEQeNkVJvj7rXW', 'Jane', 'Doe', '+1234567892', 'OWNER', 'ACTIVE', CURRENT_TIMESTAMP, true),
('customer1@email.com', '$2a$10$X5wFWMLqIqY3qRh1qmGpfOYg0ZNNXqnFvMQNJoVVEQeNkVJvj7rXW', 'Alice', 'Johnson', '+1234567893', 'USER', 'ACTIVE', CURRENT_TIMESTAMP, true),
('customer2@email.com', '$2a$10$X5wFWMLqIqY3qRh1qmGpfOYg0ZNNXqnFvMQNJoVVEQeNkVJvj7rXW', 'Bob', 'Williams', '+1234567894', 'USER', 'ACTIVE', CURRENT_TIMESTAMP, true),
('customer3@email.com', '$2a$10$X5wFWMLqIqY3qRh1qmGpfOYg0ZNNXqnFvMQNJoVVEQeNkVJvj7rXW', 'Charlie', 'Brown', '+1234567895', 'USER', 'PENDING_VERIFICATION', CURRENT_TIMESTAMP, true);

-- Insert Sample Hotels
INSERT INTO hotels (owner_id, name, description, city_id, address, rating, check_in_time, check_out_time, created_at, is_active) VALUES
(2, 'Grand Plaza Hotel', 'Luxurious 5-star hotel in the heart of the city with stunning skyline views', 1, '123 Main Street, Manhattan', 4.5, '15:00:00', '11:00:00', CURRENT_TIMESTAMP, true),
(2, 'Seaside Resort', 'Beautiful beachfront resort with private beach access', 5, '456 Beach Road, Jumeirah', 4.8, '14:00:00', '12:00:00', CURRENT_TIMESTAMP, true),
(3, 'Downtown Business Hotel', 'Modern business hotel with excellent conference facilities', 2, '789 Business Ave, Westminster', 4.2, '14:00:00', '11:00:00', CURRENT_TIMESTAMP, true),
(3, 'Historic Boutique Hotel', 'Charming boutique hotel in a restored historic building', 3, '321 Rue de Rivoli, Paris', 4.7, '15:00:00', '12:00:00', CURRENT_TIMESTAMP, true);

-- Link Hotels to Amenities
INSERT INTO hotel_amenities (hotel_id, amenities_id) VALUES
-- Grand Plaza Hotel amenities
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 11), (1, 12), (1, 13), (1, 14),
-- Seaside Resort amenities
(2, 1), (2, 2), (2, 4), (2, 5), (2, 6), (2, 7), (2, 8), (2, 10), (2, 13), (2, 14), (2, 15),
-- Downtown Business Hotel amenities
(3, 1), (3, 3), (3, 5), (3, 8), (3, 9), (3, 11), (3, 12), (3, 13), (3, 14),
-- Historic Boutique Hotel amenities
(4, 1), (4, 5), (4, 6), (4, 7), (4, 8), (4, 13), (4, 14);

-- Insert Room Types
INSERT INTO room_types (hotel_id, name, description, max_guests, bed_configuration, created_at, is_active) VALUES
-- Grand Plaza Hotel room types
(1, 'Standard Room', 'Comfortable room with city view', 2, '1 Queen Bed', CURRENT_TIMESTAMP, true),
(1, 'Deluxe Suite', 'Spacious suite with separate living area', 4, '1 King Bed + Sofa Bed', CURRENT_TIMESTAMP, true),
(1, 'Executive Suite', 'Premium suite with panoramic city views', 3, '1 King Bed', CURRENT_TIMESTAMP, true),

-- Seaside Resort room types
(2, 'Ocean View Room', 'Room with stunning ocean views', 2, '2 Double Beds', CURRENT_TIMESTAMP, true),
(2, 'Beach Villa', 'Private villa with direct beach access', 4, '1 King Bed + 2 Single Beds', CURRENT_TIMESTAMP, true),
(2, 'Presidential Suite', 'Luxury suite with private pool', 6, '2 King Beds + Sofa Bed', CURRENT_TIMESTAMP, true),

-- Downtown Business Hotel room types
(3, 'Business Single', 'Compact room perfect for business travelers', 1, '1 Single Bed', CURRENT_TIMESTAMP, true),
(3, 'Business Double', 'Spacious room with work desk', 2, '1 Queen Bed', CURRENT_TIMESTAMP, true),

-- Historic Boutique Hotel room types
(4, 'Classic Room', 'Elegantly decorated room with period features', 2, '1 Double Bed', CURRENT_TIMESTAMP, true),
(4, 'Junior Suite', 'Charming suite with antique furnishings', 3, '1 King Bed', CURRENT_TIMESTAMP, true);

-- Link Room Types to Amenities
INSERT INTO room_type_amenties (room_id, amenities_id) VALUES
-- Standard amenities for all room types
(1, 16), (1, 18), (1, 20), (1, 21), (1, 22), (1, 24), (1, 28), (1, 29), (1, 30),
(2, 16), (2, 18), (2, 19), (2, 20), (2, 21), (2, 22), (2, 23), (2, 24), (2, 25), (2, 26), (2, 28), (2, 29), (2, 30),
(3, 16), (3, 18), (3, 19), (3, 20), (3, 21), (3, 22), (3, 24), (3, 25), (3, 26), (3, 28), (3, 29), (3, 30),
(4, 16), (4, 18), (4, 20), (4, 21), (4, 22), (4, 24), (4, 25), (4, 27), (4, 28), (4, 29),
(5, 16), (5, 18), (5, 19), (5, 20), (5, 21), (5, 22), (5, 23), (5, 24), (5, 25), (5, 27), (5, 28), (5, 29), (5, 30),
(6, 16), (6, 18), (6, 19), (6, 20), (6, 21), (6, 22), (6, 23), (6, 24), (6, 25), (6, 27), (6, 28), (6, 29), (6, 30),
(7, 16), (7, 18), (7, 20), (7, 21), (7, 28), (7, 29), (7, 30),
(8, 16), (8, 18), (8, 20), (8, 21), (8, 22), (8, 28), (8, 29), (8, 30),
(9, 16), (9, 18), (9, 20), (9, 22), (9, 24), (9, 28), (9, 29),
(10, 16), (10, 18), (10, 19), (10, 20), (10, 22), (10, 23), (10, 24), (10, 25), (10, 28), (10, 29);

-- Insert Individual Rooms
INSERT INTO rooms (room_type_id, room_number, floor, accommodation_type, status, created_at, is_active) VALUES
-- Grand Plaza Hotel rooms
(1, '101', 1, 'HOTEL', 'AVAILABLE', CURRENT_TIMESTAMP, true),
(1, '102', 1, 'HOTEL', 'AVAILABLE', CURRENT_TIMESTAMP, true),
(1, '201', 2, 'HOTEL', 'OCCUPIED', CURRENT_TIMESTAMP, true),
(1, '202', 2, 'HOTEL', 'AVAILABLE', CURRENT_TIMESTAMP, true),
(2, '301', 3, 'HOTEL', 'AVAILABLE', CURRENT_TIMESTAMP, true),
(2, '302', 3, 'HOTEL', 'AVAILABLE', CURRENT_TIMESTAMP, true),
(3, '401', 4, 'HOTEL', 'AVAILABLE', CURRENT_TIMESTAMP, true),

-- Seaside Resort rooms
(4, 'A1', 1, 'RESORT', 'AVAILABLE', CURRENT_TIMESTAMP, true),
(4, 'A2', 1, 'RESORT', 'OCCUPIED', CURRENT_TIMESTAMP, true),
(5, 'V1', 0, 'VILLA', 'AVAILABLE', CURRENT_TIMESTAMP, true),
(5, 'V2', 0, 'VILLA', 'AVAILABLE', CURRENT_TIMESTAMP, true),
(6, 'PS1', 5, 'RESORT', 'AVAILABLE', CURRENT_TIMESTAMP, true),

-- Downtown Business Hotel rooms
(7, '101', 1, 'HOTEL', 'AVAILABLE', CURRENT_TIMESTAMP, true),
(7, '102', 1, 'HOTEL', 'AVAILABLE', CURRENT_TIMESTAMP, true),
(8, '201', 2, 'HOTEL', 'AVAILABLE', CURRENT_TIMESTAMP, true),
(8, '202', 2, 'HOTEL', 'MAINTENANCE', CURRENT_TIMESTAMP, true),

-- Historic Boutique Hotel rooms
(9, '1', 1, 'BED_AND_BREAKFAST', 'AVAILABLE', CURRENT_TIMESTAMP, true),
(9, '2', 1, 'BED_AND_BREAKFAST', 'AVAILABLE', CURRENT_TIMESTAMP, true),
(10, '3', 2, 'BED_AND_BREAKFAST', 'AVAILABLE', CURRENT_TIMESTAMP, true);

-- Insert Sample Bookings
INSERT INTO booking (user_id, hotel_id, room_id, check_in_date, check_out_date, num_guests, total_price, currency, status, special_requests, created_at, is_active) VALUES
(4, 1, 3, '2026-02-10', '2026-02-13', 2, 450.00, 'USD', 'CONFIRMED', 'Late check-in requested', CURRENT_TIMESTAMP, true),
(5, 2, 9, '2026-02-15', '2026-02-20', 2, 1200.00, 'USD', 'CONFIRMED', 'Honeymoon package', CURRENT_TIMESTAMP, true),
(4, 1, 1, '2026-01-20', '2026-01-23', 1, 300.00, 'USD', 'COMPLETED', NULL, CURRENT_TIMESTAMP, true),
(5, 3, 13, '2026-02-08', '2026-02-09', 1, 120.00, 'USD', 'CONFIRMED', 'Business trip', CURRENT_TIMESTAMP, true),
(4, 4, 17, '2026-03-01', '2026-03-05', 2, 800.00, 'USD', 'PENDING', 'Anniversary trip', CURRENT_TIMESTAMP, true);

-- Insert Sample Reviews
INSERT INTO reviews (booking_id, user_id, hotel_id, rating, service_rating, comment, created_at, is_active) VALUES
(3, 4, 1, 5, 5, 'Excellent hotel! The staff was very friendly and the room was spotless. Great location in Manhattan.', CURRENT_TIMESTAMP, true),
(2, 5, 2, 4, 4, 'Beautiful resort with amazing beach access. The only downside was the distance from the airport.', CURRENT_TIMESTAMP, true);

-- Summary
SELECT 'Database seeded successfully!' as status;
SELECT 'Cities: ' || COUNT(*) as count FROM cities;
SELECT 'Amenities: ' || COUNT(*) as count FROM amenities;
SELECT 'Users: ' || COUNT(*) as count FROM users;
SELECT 'Hotels: ' || COUNT(*) as count FROM hotels;
SELECT 'Room Types: ' || COUNT(*) as count FROM room_types;
SELECT 'Rooms: ' || COUNT(*) as count FROM rooms;
SELECT 'Bookings: ' || COUNT(*) as count FROM booking;
SELECT 'Reviews: ' || COUNT(*) as count FROM reviews;
