// Interfaz para BookingService
package io.aws.airboss.bookings;

import java.time.LocalDateTime;
import java.util.List;

public interface IBookingService {
    Booking createBooking(Long userId, Long flightId, int numberOfSeats,
                          String bookingStatus, LocalDateTime bookingDate, String origin,
                          String destination, LocalDateTime departureDate, String airlineName,
                          int availableSeats, String username);
    
    
    void confirmBooking(Long bookingId);
    void cancelBooking(Long bookingId);
    List<Booking> getAllBookings();
    Booking getBookingById(Long bookingId);
    
    Booking saveBooking(Booking booking);
    
    Booking updateBooking(Long id, Booking booking);
    
    void deleteBooking(Long id);
}