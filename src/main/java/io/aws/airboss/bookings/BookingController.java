package io.aws.airboss.bookings;

import io.aws.airboss.auth.CustomUserDetailsService;
import io.aws.airboss.users.User;
import io.aws.airboss.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    
    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private CustomUserDetailsService customUserDetails;
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/me")
    public ResponseEntity<List<Booking>> getMyBookings(Authentication authentication) {
        // Extrae el userId del token JWT
        String username = authentication.getName(); // Devuelve el "sub" del token
        User user = userRepository.findByUsername(username)
              .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(bookingRepository.findByUser_UserId(user.getUserId()));
    }
    
    
    
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequestDTO request) {
        Booking booking = bookingService.createBooking(request.getUserId(), request.getFlightId(), request.getNumberOfSeats());
        return ResponseEntity.ok(booking);
    }
    
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return ResponseEntity.ok(bookings);
    }
    
    @PostMapping("/confirm/{bookingId}")
    public ResponseEntity<String> confirmBooking(@PathVariable Long bookingId) {
        try {
            bookingService.confirmBooking(bookingId);
            return ResponseEntity.ok("Reserva confirmada exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/cancel/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
        try {
            bookingService.cancelBooking(bookingId);
            return ResponseEntity.ok("Reserva cancelada exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
