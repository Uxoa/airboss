-- Datos de prueba para perfiles
INSERT INTO profiles (email, last_name, mobile, name, registration_date)
VALUES
    ('john.doe@example.com', 'Doe', 1234567890, 'John Doe', NOW()),
    ('paloma@example.com', 'Paloma', 987654321, 'Paloma User', NOW());

-- Datos de prueba para usuarios
INSERT INTO users (username, password, profile_id)
VALUES
    ('admin', '$2a$12$1EpeTJwnwc.YNTufwsLdbeG7KescccuuLzxG./meDxoJe.LzumLRy', 1),
    ('paloma', '$2a$12$1EpeTJwnwc.YNTufwsLdbeG7KescccuuLzxG./meDxoJe.LzumLRy', 2);

-- Datos de prueba para roles
INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'), ('ROLE_USER');

-- Asignar roles a usuarios
INSERT INTO user_roles (user_id, role_id)
VALUES
    (1, 1), -- Admin user assigned ADMIN role
    (1, 2), -- Admin user also assigned USER role
    (2, 2); -- Paloma assigned USER role only

-- Datos de prueba para aeropuertos
INSERT INTO airports (name,city, country_iso_code, iata_code)
VALUES ('John F. Kennedy International Airport', 'New York','USA', 'JFK'),
       ('Los Angeles International Airport', 'Los Angeles','USA', 'LAX');

-- Datos de prueba para vuelos
INSERT INTO flights (flight_number, departure, arrival, origin, destination, departure_time, total_seats, available_seats, is_available, airline_name)
VALUES
    ('FL123', '2025-01-23 10:00:00', '2025-01-23 14:00:00', 'JFK', 'LAX', '2025-01-23 10:00:00', 180, 150, TRUE, 'Delta Airlines'),
    ('FL456', '2025-01-23 16:00:00', '2025-01-23 20:00:00', 'JFK', 'ORD', '2025-01-23 16:00:00', 200, 200, TRUE, 'American Airlines'),
    ('FL789', '2025-01-24 08:00:00', '2025-01-24 12:00:00', 'LAX', 'SEA', '2025-01-24 08:00:00', 150, 75, TRUE, 'United Airlines'),
    ('FL101', '2025-01-25 06:00:00', '2025-01-25 09:00:00', 'ORD', 'ATL', '2025-01-25 06:00:00', 120, 0, FALSE, 'Southwest Airlines'),
    ('FL202', '2025-01-25 14:30:00', '2025-01-25 18:00:00', 'ATL', 'MIA', '2025-01-25 14:30:00', 180, 160, TRUE, 'Spirit Airlines'),
    ('FL303', '2025-01-26 12:00:00', '2025-01-26 15:30:00', 'SEA', 'DEN', '2025-01-26 12:00:00', 140, 100, TRUE, 'Alaska Airlines'),
    ('FL404', '2025-01-27 18:00:00', '2025-01-27 22:00:00', 'DEN', 'JFK', '2025-01-27 18:00:00', 160, 80, TRUE, 'JetBlue'),
    ('FL505', '2025-01-28 07:00:00', '2025-01-28 10:00:00', 'MIA', 'BOS', '2025-01-28 07:00:00', 200, 200, TRUE, 'Frontier Airlines'),
    ('FL606', '2025-01-29 11:30:00', '2025-01-29 14:30:00', 'BOS', 'JFK', '2025-01-29 11:30:00', 120, 110, TRUE, 'Delta Airlines'),
    ('FL707', '2025-01-30 20:00:00', '2025-01-31 01:00:00', 'JFK', 'SFO', '2025-01-30 20:00:00', 300, 250, TRUE, 'United Airlines');

-- Datos de prueba para reservas
INSERT INTO bookings (user_id, flight_id, number_of_seats, booking_date, status)
VALUES
    (1, 1, 2, NOW(), 'CONFIRMED'),
    (2, 1, 1, NOW(), 'PENDING'); -- Example booking for Paloma
