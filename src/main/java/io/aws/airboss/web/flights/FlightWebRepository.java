package io.aws.airboss.web.flights;

import io.aws.airboss.flights.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightWebRepository extends JpaRepository<Flight, Long> {
    
    Optional<Flight> findFlightByFlightId(Long flightId); // Cambia a flightId
    
    List<Flight> findByOriginAndDestinationAndDepartureTimeBetween(
          String origin, String destination, LocalDateTime fromTime, LocalDateTime toTime
    );
    
    @Query("SELECT f FROM Flight f WHERE f.origin = :origin AND f.destination = :destination AND f.departureTime = :departureTime")
    List<Flight> searchFlights(
          @Param("origin") String origin,
          @Param("destination") String destination,
          @Param("departureTime") LocalDateTime departureTime
    );
    
    @Query("SELECT f FROM Flight f WHERE f.origin = :origin AND f.destination = :destination AND f.departureTime BETWEEN :startDate AND :endDate")
    List<Flight> findFlightsByCriteria(
          @Param("origin") String origin,
          @Param("destination") String destination,
          @Param("startDate") LocalDateTime startDate,
          @Param("endDate") LocalDateTime endDate
    );
}
