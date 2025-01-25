package io.aws.airboss.web;

import io.aws.airboss.bookings.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WebBookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE b.user.username = :username")
    List<Booking> findAllByUsername(@Param("username") String username);
    
}
