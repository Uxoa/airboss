package io.aws.airboss.web;

import io.aws.airboss.bookings.Booking;
import io.aws.airboss.flights.Flight;
import io.aws.airboss.users.User;

import java.util.List;
import java.util.Optional;

public interface GlobalWebRepository {
    Optional<User> findUserByUsername(String username);
    
    List<Booking> findBookingsByUsername(String username);
    
    void cancelBooking(String username, Long bookingId);
    List<String> getAllAirports();
    List<Flight> searchFlights(String from, String to, String date);
    void createBooking(String username, Long flightId);
    Optional<Flight> findFlightById(Long flightId);
    Optional<String> findUsernameByBookingId(Long bookingId);
}
