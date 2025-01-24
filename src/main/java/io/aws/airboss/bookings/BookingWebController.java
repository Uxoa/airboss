package io.aws.airboss.bookings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookings")
public class BookingWebController {
    
    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @GetMapping
    public String listBookings(Model model) {
        model.addAttribute("bookings", bookingRepository.findAll());
        return "bookings/list";
    }
    
    @GetMapping("/{id}")
    public String viewBooking(@PathVariable Long id, Model model) {
        Booking booking = bookingRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        model.addAttribute("booking", booking);
        return "bookings/detail";
    }
    
    @GetMapping("/create")
    public String createBookingForm() {
        return "bookings/create";
    }
    
    @PostMapping
    public String createBooking(@RequestParam Long userId,
                                @RequestParam Long flightId,
                                @RequestParam int numberOfSeats) {
        bookingService.createBooking(userId, flightId, numberOfSeats);
        return "redirect:/bookings";
    }
    
    @PostMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return "redirect:/bookings";
    }
}
