package io.aws.airboss.web;

import io.aws.airboss.bookings.Booking;
import io.aws.airboss.flights.Flight;
import io.aws.airboss.users.User;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GlobalWebRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    
  
    
   
    
}
