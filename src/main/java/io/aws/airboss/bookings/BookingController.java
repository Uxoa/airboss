package io.aws.airboss.bookings;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    
    private final IBookingService bookingService;
    
    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<String> createBooking(@RequestBody BookingDTO request) {
        Booking booking = bookingService.createBooking(
              request.getUserId(),
              request.getFlightId(),
              request.getNumberOfSeats()
        );
        
        if (booking == null) {
            // Error al crear la reserva
            return ResponseEntity
                  .status(HttpStatus.BAD_REQUEST)
                  .body("Error processing booking.");
        }
        
        // Devuelves SOLO la ruta a la que quieres redirigir en tu frontend
        // sin 'redirect:' al comienzo.
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
              booking.getFlight().getFlightId(),
              booking.getNumberOfSeats()
        );
        
        return ResponseEntity.ok(bookingDTO);
    }
    @GetMapping("/list")
    public ResponseEntity<Iterable<Booking>> listBookings() {
        Iterable<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings); // ✅ Devuelve JSON con todas las reservas
    }
}