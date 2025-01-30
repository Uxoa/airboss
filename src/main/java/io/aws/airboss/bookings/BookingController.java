package io.aws.airboss.bookings;

import io.aws.airboss.flights.Flight;
import io.aws.airboss.flights.IFlightService;
import io.aws.airboss.users.User;
import io.aws.airboss.users.UserRepository;
import io.aws.airboss.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    
    private final IBookingService bookingService;
    private final IFlightService flightService;
    private final UserService userService;
    private final UserRepository userRepository;
    
    @Autowired
    public BookingController(IBookingService bookingService, IFlightService flightService, UserService userService, UserRepository userRepository) {
        this.bookingService = bookingService;
        this.flightService = flightService;
        this.userService = userService;
        this.userRepository = userRepository;
    }
    
    @PostMapping("/create")
    public ResponseEntity<String> createBooking(@RequestBody BookingDTO request) {
        User user = userRepository.findById(request.getUserId()).orElse(null);
        Flight flight = flightService.getFlightById(request.getFlightId());
        
        if (user == null || flight == null) {
            return ResponseEntity.badRequest().body("User or Flight not found.");
        }
        
        Booking booking = new Booking();
        booking.getUser().setUserId(user.getUserId());
        booking.setFlight(flight);
        booking.setNumberOfSeats(request.getNumberOfSeats());
        booking.setStatus(BookingStatus.PENDING);
        
        booking = bookingService.saveBooking(booking);
        return ResponseEntity.ok("/confirm?bookingId=" + booking.getBookingId());
    }
    
    @GetMapping("/confirm")
    public String showConfirmationPage(@RequestParam Long bookingId, Model model) {
        model.addAttribute("bookingId", bookingId);
        return "confirm"; // ✅ Renderiza `confirm.mustache`
    }
    
    @GetMapping("/details/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingDetails(@PathVariable Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        
        if (booking == null) {
            return ResponseEntity.notFound().build();
        }
        
        BookingDTO bookingDTO = new BookingDTO(
              booking.getBookingId(),
              booking.getUser(),
              booking.getFlight(),
              booking.getNumberOfSeats()
        );
        
        return ResponseEntity.ok(bookingDTO); // ✅ Devuelve JSON con los detalles completos
    }
}