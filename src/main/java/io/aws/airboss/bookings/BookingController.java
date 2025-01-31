package io.aws.airboss.bookings;

import io.aws.airboss.flights.Flight;
import io.aws.airboss.users.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.channels.AcceptPendingException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    
    private final IBookingService bookingService;
    
    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<String> createBooking(@RequestBody BookingDTO request) {
        // Asegurarnos de que bookingDate nunca sea nulo
        LocalDateTime bookingDate = request.getBookingDate();
        if (bookingDate == null) {
            bookingDate = LocalDateTime.now(); // Valor por defecto
        }
        
        Booking booking = bookingService.createBooking(
              request.getUserId(),
              request.getFlightId(),
              request.getNumberOfSeats(),
              "PENDING",
              bookingDate,
              request.getOrigin(),
              request.getDestination(),
              request.getDepartureTime(),
              request.getAirlineName(),
              request.getAvailableSeats(),
              request.getUsername()
        );
        
        if (booking == null) {
            return ResponseEntity
                  .status(HttpStatus.BAD_REQUEST)
                  .body("Error processing booking.");
        }
        
        String url = "/flights/search/confirmacion?bookingId=" + booking.getBookingId();
        return ResponseEntity.ok(url);
    }
    
    @GetMapping("/details/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingDetails(@PathVariable Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            return ResponseEntity.notFound().build();
        }
        
        BookingDTO bookingDTO = new BookingDTO(
              booking.getBookingId(),
              booking.getUser().getUserId(),
              booking.getUser().getUsername(),
              booking.getFlight().getFlightId(),
              booking.getFlight().getAirlineName(),
              booking.getFlight().getAvailableSeats(),
              booking.getFlight().getOrigin(),
              booking.getFlight().getDestination(),
              booking.getFlight().getDepartureTime(),
              booking.getNumberOfSeats(),
              booking.getStatus(),
              booking.getBookingDate()
        );
        
        return ResponseEntity.ok(bookingDTO);
    }
    
    @GetMapping("/list")
    public ResponseEntity<Iterable<Booking>> listBookings() {
        Iterable<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings); // ✅ Devuelve JSON con todas las reservas
    }
}