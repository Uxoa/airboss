package io.aws.airboss.flights;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    // Consulta para buscar vuelos con los criterios dados
    List<Flight> findByOriginAndDestinationAndDepartureTimeAfterAndAvailableSeatsGreaterThan(
          String origin, String destination, LocalDateTime date, int minSeats);
}

