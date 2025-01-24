-- Eliminar tablas existentes
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS flights;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS profiles;

-- Crear tabla de perfiles
CREATE TABLE IF NOT EXISTS profiles (
                                        profile_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        email VARCHAR(255) NOT NULL UNIQUE,
                                        last_login DATETIME(6),
                                        last_name VARCHAR(255) NOT NULL,
                                        mobile BIGINT NOT NULL,
                                        name VARCHAR(255) NOT NULL,
                                        profile_image VARCHAR(255),
                                        registration_date DATETIME(6)
);

-- Crear tabla de usuarios
CREATE TABLE IF NOT EXISTS users (
                                     user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
                                     profile_id BIGINT NOT NULL,
                                     CONSTRAINT fk_user_profile FOREIGN KEY (profile_id) REFERENCES profiles(profile_id)
);

-- Crear tabla de roles
CREATE TABLE IF NOT EXISTS roles (
                                     role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL UNIQUE
);

-- Crear tabla de asignación de roles a usuarios
CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id BIGINT NOT NULL,
                                          role_id BIGINT NOT NULL,
                                          PRIMARY KEY (user_id, role_id),
                                          CONSTRAINT fk_user_role FOREIGN KEY (user_id) REFERENCES users(user_id),
                                          CONSTRAINT fk_role_user FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

-- Crear tabla de aeropuertos
CREATE TABLE IF NOT EXISTS airports (
                                       airport_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
                                       city VARCHAR(255) NOT NULL,
                                       country_iso_code VARCHAR(2) NOT NULL,
                                       iata_code VARCHAR(3) NOT NULL
);

-- Crear tabla de vuelos
CREATE TABLE IF NOT EXISTS flights (
                                       flight_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       flight_number VARCHAR(255) NOT NULL UNIQUE,
                                       departure DATETIME NOT NULL,
                                       arrival DATETIME NOT NULL,
                                       origin VARCHAR(255) NOT NULL,
                                       destination VARCHAR(255) NOT NULL,
                                       departure_time DATETIME NOT NULL,
                                       total_seats INT NOT NULL,
                                       available_seats INT NOT NULL,
                                       is_available BOOLEAN NOT NULL,
                                       airline_name VARCHAR(255) NOT NULL

);

-- Crear tabla de reservas
CREATE TABLE IF NOT EXISTS bookings (
                                        booking_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        user_id BIGINT NOT NULL,
                                        flight_id BIGINT NOT NULL,
                                        number_of_seats INT NOT NULL,
                                        booking_date DATETIME NOT NULL,
                                        status ENUM('CONFIRMED', 'PENDING', 'CANCELLED') NOT NULL,
                                        CONSTRAINT fk_user_booking FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
                                        CONSTRAINT fk_flight_booking FOREIGN KEY (flight_id) REFERENCES flights(flight_id) ON DELETE CASCADE
);
