// Interfaz para BookingService
package io.aws.airboss.bookings;

import java.util.List;

public interface IBookingService {
    Booking createBooking(Long userId, Long flightId, int numberOfSeats);
    void confirmBooking(Long bookingId);
    void cancelBooking(Long bookingId);
    List<Booking> getAllBookings();
    Booking getBookingById(Long bookingId);
    
    Booking saveBooking(Booking booking);
    
    Booking updateBooking(Long id, Booking booking);
    
    void deleteBooking(Long id);
}