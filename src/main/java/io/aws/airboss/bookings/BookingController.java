package io.aws.airboss.bookings;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String createBooking(@RequestBody BookingRequestDTO request, Model model) {
        Booking booking = bookingService.createBooking(request.getUserId(), request.getFlightId(), request.getAvailableSeats());
        
        if (booking == null) {
            model.addAttribute("error", "Error processing booking.");
            return "error";
        }
        
        model.addAttribute("bookingId", booking.getBookingId());
        return "confirm";
    }
    
    @PostMapping("/confirm/{bookingId}")
    public String confirmBooking(@PathVariable Long bookingId, Model model) {
        bookingService.confirmBooking(bookingId);
        model.addAttribute("bookingId", bookingId);
        return "confirm";
    }
}
