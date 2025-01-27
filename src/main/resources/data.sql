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
INSERT INTO airports (name, city, country_iso_code, iata_code)
VALUES
    ('John F. Kennedy International Airport', 'New York', 'USA', 'JFK'),
    ('Los Angeles International Airport', 'Los Angeles', 'USA', 'LAX'),
    ('O\'Hare International Airport', 'Chicago', 'USA', 'ORD'),
    ('Hartsfield-Jackson Atlanta International Airport', 'Atlanta', 'USA', 'ATL'),
    ('Miami International Airport', 'Miami', 'USA', 'MIA'),
    ('Seattle-Tacoma International Airport', 'Seattle', 'USA', 'SEA'),
    ('Denver International Airport', 'Denver', 'USA', 'DEN'),
    ('San Francisco International Airport', 'San Francisco', 'USA', 'SFO'),
    ('Logan International Airport', 'Boston', 'USA', 'BOS'),
    ('Dallas/Fort Worth International Airport', 'Dallas', 'USA', 'DFW'),
    ('Orlando International Airport', 'Orlando', 'USA', 'MCO'),
    ('Las Vegas Harry Reid International Airport', 'Las Vegas', 'USA', 'LAS'),
    ('Charlotte Douglas International Airport', 'Charlotte', 'USA', 'CLT'),
    ('Phoenix Sky Harbor International Airport', 'Phoenix', 'USA', 'PHX'),
    ('George Bush Intercontinental Airport', 'Houston', 'USA', 'IAH'),
    ('Philadelphia International Airport', 'Philadelphia', 'USA', 'PHL'),
    ('Salt Lake City International Airport', 'Salt Lake City', 'USA', 'SLC'),
    ('Detroit Metropolitan Wayne County Airport', 'Detroit', 'USA', 'DTW'),
    ('Minneapolis-Saint Paul International Airport', 'Minneapolis', 'USA', 'MSP'),
    ('Washington Dulles International Airport', 'Washington, D.C.', 'USA', 'IAD');

-- Datos de prueba para vuelos
INSERT INTO flights (flight_number, departure, arrival, origin, destination, departure_time, total_seats, available_seats, is_available, airline_name)
VALUES
    ('FL801', '2025-01-27 10:00:00', '2025-01-27 14:00:00', 'JFK', 'LAX', '2025-01-27 10:00:00', 180, 160, TRUE, 'Delta Airlines'),
    ('FL802', '2025-01-28 16:00:00', '2025-01-28 20:00:00', 'JFK', 'ORD', '2025-01-28 16:00:00', 200, 180, TRUE, 'American Airlines'),
    ('FL803', '2025-01-29 08:00:00', '2025-01-29 12:00:00', 'LAX', 'SEA', '2025-01-29 08:00:00', 150, 100, TRUE, 'United Airlines'),
    ('FL804', '2025-01-30 06:00:00', '2025-01-30 09:00:00', 'ORD', 'ATL', '2025-01-30 06:00:00', 120, 60, TRUE, 'Southwest Airlines'),
    ('FL805', '2025-01-31 14:30:00', '2025-01-31 18:00:00', 'ATL', 'MIA', '2025-01-31 14:30:00', 180, 170, TRUE, 'Spirit Airlines'),
    ('FL806', '2025-02-01 12:00:00', '2025-02-01 15:30:00', 'SEA', 'DEN', '2025-02-01 12:00:00', 140, 130, TRUE, 'Alaska Airlines'),
    ('FL807', '2025-02-02 18:00:00', '2025-02-02 22:00:00', 'DEN', 'JFK', '2025-02-02 18:00:00', 160, 90, TRUE, 'JetBlue'),
    ('FL808', '2025-02-03 07:00:00', '2025-02-03 10:00:00', 'MIA', 'BOS', '2025-02-03 07:00:00', 200, 190, TRUE, 'Frontier Airlines'),
    ('FL809', '2025-02-04 11:30:00', '2025-02-04 14:30:00', 'BOS', 'JFK', '2025-02-04 11:30:00', 120, 115, TRUE, 'Delta Airlines'),
    ('FL810', '2025-02-05 20:00:00', '2025-02-06 01:00:00', 'JFK', 'SFO', '2025-02-05 20:00:00', 300, 270, TRUE, 'United Airlines'),
    ('FL811', '2025-02-06 10:00:00', '2025-02-06 14:00:00', 'LAX', 'ATL', '2025-02-06 10:00:00', 180, 150, TRUE, 'Delta Airlines'),
    ('FL812', '2025-02-07 16:00:00', '2025-02-07 20:00:00', 'ATL', 'ORD', '2025-02-07 16:00:00', 200, 180, TRUE, 'American Airlines'),
    ('FL813', '2025-02-08 08:00:00', '2025-02-08 12:00:00', 'JFK', 'SEA', '2025-02-08 08:00:00', 150, 140, TRUE, 'United Airlines'),
    ('FL814', '2025-02-09 06:00:00', '2025-02-09 09:00:00', 'SFO', 'LAX', '2025-02-09 06:00:00', 120, 60, TRUE, 'Southwest Airlines'),
    ('FL815', '2025-02-10 14:30:00', '2025-02-10 18:00:00', 'SEA', 'DEN', '2025-02-10 14:30:00', 180, 160, TRUE, 'Spirit Airlines'),
    ('FL816', '2025-02-11 12:00:00', '2025-02-11 15:30:00', 'DEN', 'JFK', '2025-02-11 12:00:00', 140, 110, TRUE, 'Alaska Airlines'),
    ('FL817', '2025-02-12 18:00:00', '2025-02-12 22:00:00', 'BOS', 'LAX', '2025-02-12 18:00:00', 160, 140, TRUE, 'JetBlue'),
    ('FL818', '2025-02-13 07:00:00', '2025-02-13 10:00:00', 'MIA', 'JFK', '2025-02-13 07:00:00', 200, 180, TRUE, 'Frontier Airlines'),
    ('FL819', '2025-02-14 11:30:00', '2025-02-14 14:30:00', 'ORD', 'SEA', '2025-02-14 11:30:00', 120, 100, TRUE, 'Delta Airlines'),
    ('FL820', '2025-02-15 20:00:00', '2025-02-16 01:00:00', 'JFK', 'LAX', '2025-02-15 20:00:00', 300, 290, TRUE, 'United Airlines');

-- Datos de prueba para reservas
INSERT INTO bookings (user_id, flight_id, number_of_seats, booking_date, status)
VALUES
    (1, 1, 2, NOW(), 'CONFIRMED'),
    (2, 1, 1, NOW(), 'PENDING'); -- Example booking for Paloma
