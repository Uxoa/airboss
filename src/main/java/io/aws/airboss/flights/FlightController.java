package io.aws.airboss.flights;

import io.aws.airboss.bookings.Booking;
import io.aws.airboss.bookings.BookingServiceImpl;
import io.aws.airboss.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/flights")
public class FlightController {
    
    private final IFlightService flightService;
    private final UserService userService;
    private final BookingServiceImpl bookingService;
    
    @Autowired
    public FlightController(IFlightService flightService, UserService userService, BookingServiceImpl bookingService) {
        this.flightService = flightService;
        this.userService = userService;
        this.bookingService = bookingService;
    }
    
    @GetMapping("/search/resultados")
    public String searchFlights(
          
          @RequestParam String origin,
          @RequestParam String destination,
          @RequestParam String date,
          Model model,
          Principal principal) {
        
        LocalDate departureDate = LocalDate.parse(date);
        List<FlightDTO> flights = flightService.searchFlights(origin, destination, departureDate);
        
        if (flights.isEmpty()) {
            model.addAttribute("error", "No flights found for the selected criteria.");
        } else {
            model.addAttribute("flights", flights);
        }
        
        String username = principal.getName();
        Long userId = userService.getUserIdByUsername(username);
        model.addAttribute("userId", userId);
        
        model.addAttribute("title", "Search Results");
        return "results";
    }
    
    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        return ResponseEntity.ok(flightService.createFlight(flight));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        return ResponseEntity.ok(flightService.updateFlight(id, flight));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search/confirmacion")
    public String confirmBooking(@RequestParam Long bookingId, Model model) {
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            model.addAttribute("error", "Booking not found.");
            return "error"; // Return error view
        }
        
        model.addAttribute("booking", booking);
        return "confirm"; // Return confirmation view
    }
}
