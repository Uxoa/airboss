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
INSERT INTO flights (flight_number, departure, arrival, origin, destination)
VALUES
    ('FL123', '2025-01-23 10:00:00', '2025-01-23 14:00:00', 'JFK', 'LAX');

-- Datos de prueba para reservas
INSERT INTO bookings (user_id, flight_id, number_of_seats, booking_date, status)
VALUES
    (1, 1, 2, NOW(), 'CONFIRMED'),
    (2, 1, 1, NOW(), 'PENDING'); -- Example booking for Paloma
