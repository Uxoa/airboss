package io.aws.airboss.bookings;

import io.aws.airboss.flights.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser_UserId(Long userId);
    Optional<Booking> findById(Long id);
}

