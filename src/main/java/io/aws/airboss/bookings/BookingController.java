package io.aws.airboss.bookings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/bookings")
public class BookingController {
    
    private final IBookingService bookingService;
    
    @Autowired
    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<String> createBooking(@RequestBody BookingRequestDTO request) {
        Booking booking = bookingService.createBooking(request.getUserId(), request.getFlightId(), request.getAvailableSeats());
        
        if (booking == null) {
            return ResponseEntity.badRequest().body("Error processing booking.");
        }
        
        return ResponseEntity.ok("/api/bookings/confirm?bookingId=" + booking.getBookingId()); // ✅ Redirige correctamente
    }
    
    
    
    @GetMapping("/confirm")
    public String showConfirmationPage(@RequestParam Long bookingId, Model model) {
        model.addAttribute("bookingId", bookingId);
        return "confirm"; // ✅ Renderiza `confirm.mustache`
    }
    
}
