package io.aws.airboss.web;

import io.aws.airboss.bookings.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookingWebRepository extends JpaRepository<Booking, Long> {
    
    @Query("SELECT b FROM Booking b WHERE b.user.username = :username")
    List<Booking> findBookingsByUsername(@Param("username") String username);
    
    Optional<Booking> findById(Long id);
    
    
    @Query(value = """
        SELECT
            flight_number AS flightNumber,
            origin AS origin,
            destination AS destination,
            departure_time AS departureTime
        FROM flights
        WHERE origin = :origin
          AND destination = :destination
          AND departure_time BETWEEN :fromTime AND :toTime
        """, nativeQuery = true)
    
    List<Map<String, Object>> findFlightsByCriteria(
          @Param("origin") String origin,
          @Param("destination") String destination,
          @Param("fromTime") LocalDateTime fromTime,
          @Param("toTime") LocalDateTime toTime);
}
