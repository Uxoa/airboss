-- Insertar datos en perfiles
INSERT INTO profiles (email, last_name, mobile, name, registration_date)
VALUES
    ('john.doe@example.com', 'Doe', 1234567890, 'John Doe', NOW()),
    ('paloma@example.com', 'Paloma', 987654321, 'Paloma User', NOW());

-- Insertar datos en usuarios
INSERT INTO users (username, password, profile_id)
VALUES
    ('admin', '$2a$12$1EpeTJwnwc.YNTufwsLdbeG7KescccuuLzxG./meDxoJe.LzumLRy', 1),
    ('paloma', '$2a$12$1EpeTJwnwc.YNTufwsLdbeG7KescccuuLzxG./meDxoJe.LzumLRy', 2);

-- Insertar datos en roles
INSERT INTO roles (name)
VALUES
    ('ROLE_ADMIN'),
    ('ROLE_USER');

-- Asignar roles a usuarios
INSERT INTO user_roles (user_id, role_id)
VALUES
    (1, 1), -- Admin tiene rol ADMIN
    (1, 2), -- Admin también tiene rol USER
    (2, 2); -- Paloma tiene rol USER únicamente

-- Insertar datos en aeropuertos
INSERT INTO airports (name, city, country_iso_code, iata_code)
VALUES
    ('John F. Kennedy International Airport', 'New York', 'US', 'JFK'),
    ('Los Angeles International Airport', 'Los Angeles', 'US', 'LAX'),
    ('O\'Hare International Airport', 'Chicago', 'US', 'ORD'),
    ('Hartsfield-Jackson Atlanta International Airport', 'Atlanta', 'US', 'ATL'),
    ('Miami International Airport', 'Miami', 'US', 'MIA'),
    ('Seattle-Tacoma International Airport', 'Seattle', 'US', 'SEA'),
    ('Denver International Airport', 'Denver', 'US', 'DEN'),
    ('San Francisco International Airport', 'San Francisco', 'US', 'SFO'),
    ('Logan International Airport', 'Boston', 'US', 'BOS'),
    ('Dallas/Fort Worth International Airport', 'Dallas', 'US', 'DFW');

-- Insertar datos en vuelos
INSERT INTO flights (flight_number, departure, arrival, origin, destination, departure_time, total_seats, available_seats, is_available, airline_name)
VALUES

    -- 28 de enero
    ('FL802', '2025-01-28 08:00:00', '2025-01-28 12:00:00', 'JFK', 'ORD', '2025-01-28 08:00:00', 200, 180, TRUE, 'American Airlines'),
    ('FL823', '2025-01-28 13:00:00', '2025-01-28 16:00:00', 'ORD', 'ATL', '2025-01-28 13:00:00', 180, 170, TRUE, 'Delta Airlines'),
    ('FL824', '2025-01-28 17:00:00', '2025-01-28 20:00:00', 'ATL', 'MIA', '2025-01-28 17:00:00', 150, 140, TRUE, 'Spirit Airlines'),

    -- 29 de enero
    ('FL803', '2025-01-29 08:00:00', '2025-01-29 12:00:00', 'LAX', 'SEA', '2025-01-29 08:00:00', 150, 100, TRUE, 'United Airlines'),
    ('FL825', '2025-01-29 14:00:00', '2025-01-29 18:00:00', 'SEA', 'JFK', '2025-01-29 14:00:00', 200, 180, TRUE, 'JetBlue'),

    -- 30 de enero
    ('FL804', '2025-01-30 06:00:00', '2025-01-30 09:00:00', 'ORD', 'ATL', '2025-01-30 06:00:00', 120, 60, TRUE, 'Southwest Airlines'),
    ('FL826', '2025-01-30 10:00:00', '2025-01-30 13:30:00', 'ATL', 'SEA', '2025-01-30 10:00:00', 150, 140, TRUE, 'Frontier Airlines'),
    ('FL827', '2025-01-30 14:00:00', '2025-01-30 18:00:00', 'SEA', 'ORD', '2025-01-30 14:00:00', 180, 170, TRUE, 'Alaska Airlines'),

    -- 31 de enero
    ('FL805', '2025-01-31 08:00:00', '2025-01-31 12:00:00', 'ATL', 'MIA', '2025-01-31 08:00:00', 180, 170, TRUE, 'Spirit Airlines'),
    ('FL828', '2025-01-31 13:00:00', '2025-01-31 17:00:00', 'MIA', 'LAX', '2025-01-31 13:00:00', 200, 190, TRUE, 'United Airlines'),
    ('FL829', '2025-01-31 18:00:00', '2025-01-31 21:30:00', 'LAX', 'JFK', '2025-01-31 18:00:00', 150, 140, TRUE, 'Delta Airlines'),

    -- 1 de febrero
    ('FL830', '2025-02-01 08:00:00', '2025-02-01 12:00:00', 'SEA', 'JFK', '2025-02-01 08:00:00', 180, 160, TRUE, 'JetBlue'),
    ('FL831', '2025-02-01 14:00:00', '2025-02-01 18:00:00', 'JFK', 'ORD', '2025-02-01 14:00:00', 200, 190, TRUE, 'United Airlines'),

    -- 2 de febrero
    ('FL832', '2025-02-02 08:00:00', '2025-02-02 12:00:00', 'ORD', 'SEA', '2025-02-02 08:00:00', 140, 110, TRUE, 'American Airlines');

-- Insertar datos en reservas
INSERT INTO bookings (user_id, flight_id, number_of_seats, booking_date, status)
VALUES
    (1, 1, 2, NOW(), 'CONFIRMED'),
    (2, 2, 1, NOW(), 'PENDING');