package io.aws.airboss.web;

import io.aws.airboss.flights.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightWebRepository extends JpaRepository<Flight, Long> {
    
    // Buscar un vuelo por ID
    Optional<Flight> findFlightByFlightId(Long flightId);
    
    
    // Buscar vuelos exactos usando JPQL
    @Query("SELECT f FROM Flight f WHERE f.origin = :origin AND f.destination = :destination AND f.departureTime = :departureTime")
    List<Flight> searchFlights(
          @Param("origin") String origin,
          @Param("destination") String destination,
          @Param("departureTime") LocalDateTime departureTime
    );
    
    // Buscar vuelos con rango de fechas y parámetros de búsqueda
    @Query("""
        SELECT f FROM Flight f
        WHERE f.origin = :origin
        AND f.destination = :destination
        AND f.departureTime BETWEEN :startDate AND :endDate
    """)
    List<Flight> findFlightsByCriteria(
          @Param("origin") String origin,
          @Param("destination") String destination,
          @Param("startDate") LocalDateTime startDate,
          @Param("endDate") LocalDateTime endDate
    );
    
    // Buscar vuelos del mismo día pero en horas distintas
    @Query("""
        SELECT f FROM Flight f
        WHERE f.origin = :from
        AND f.destination = :to
        AND f.departureTime BETWEEN :startOfDay AND :endOfDay
        AND f.departureTime <> :exactTime
        ORDER BY f.departureTime ASC
    """)
    List<Flight> findFlightsBySameDayOtherTimes(
          @Param("from") String from,
          @Param("to") String to,
          @Param("startOfDay") LocalDateTime startOfDay,
          @Param("endOfDay") LocalDateTime endOfDay,
          @Param("exactTime") LocalDateTime exactTime
    );
    
    // Buscar vuelos por origen con asientos disponibles
    @Query("""
        SELECT f FROM Flight f
        WHERE f.origin = :origin
        AND f.availableSeats > 0
        AND f.isAvailable = true
    """)
    List<Flight> findAvailableFlightsByOrigin(@Param("origin") String origin);
    
    // Buscar vuelos por destino con asientos disponibles
    @Query("""
        SELECT f FROM Flight f
        WHERE f.destination = :destination
        AND f.availableSeats > 0
        AND f.isAvailable = true
    """)
    List<Flight> findAvailableFlightsByDestination(@Param("destination") String destination);
    
    // Buscar los vuelos más recientes disponibles
    @Query("""
        SELECT f FROM Flight f
        WHERE f.isAvailable = true
        ORDER BY f.departureTime ASC
    """)
    List<Flight> findNextAvailableFlights();
    
    
    /**
     * Encuentra vuelos por origen, destino y fecha de salida exacta.
     */
    @Query("SELECT f FROM Flight f WHERE f.origin = :origin AND f.destination = :destination AND DATE(f.departureTime) = :departureDate")
    List<Flight> findByOriginAndDestinationAndDepartureDate(
          @Param("origin") String origin,
          @Param("destination") String destination,
          @Param("departureDate") LocalDate departureDate
    );
    
    /**
     * Encuentra los 5 primeros vuelos disponibles ordenados por tiempo de salida ascendente.
     */
    List<Flight> findTop5ByIsAvailableOrderByDepartureTimeAsc(boolean isAvailable);
    
    
}
