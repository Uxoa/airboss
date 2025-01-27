package io.aws.airboss.web.bookings;

import io.aws.airboss.bookings.Booking;
import io.aws.airboss.flights.Flight;
import io.aws.airboss.web.GlobalWebServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class BookingWebController {
    
    private final GlobalWebServices globalWebServices;
    
    public BookingWebController(GlobalWebServices globalWebServices) {
        this.globalWebServices = globalWebServices;
    }
    
    @GetMapping("/reservas/view")
    public String viewReservations(Model model, Principal principal) {
        String username = principal.getName();
        List<Booking> bookings = globalWebServices.getBookingsByUser(username);
        
        model.addAttribute("title", "Mis Reservas");
        model.addAttribute("bookings", bookings); // La vista ahora manejará los mapas directamente
        return "reservas/view";
    }
    
    
    /** Reservas del usuario */
    @GetMapping("/mybookings")
    public String userBookings(Model model, Principal principal) {
        model.addAttribute("title", "My Bookings");
        model.addAttribute("bookings", globalWebServices.getBookingsByUser(principal.getName()));
        return "bookings";
    }
    
    
    /** Cancelar una reserva */
    @PostMapping("/reservas/cancelar/{id}")
    public String cancelBooking(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        globalWebServices.cancelBooking(username, id);
        return "redirect:/usuario"; // Redirigir a la página de perfil después de cancelar
    }
    
    @GetMapping("/flights/search")
    public String searchFlights(@RequestParam String origin,
                                @RequestParam String destination,
                                @RequestParam String fromTime,
                                @RequestParam String toTime,
                                Model model) {
        LocalDateTime from = LocalDateTime.parse(fromTime);
        LocalDateTime to = LocalDateTime.parse(toTime);
        
        List<Flight> flights = globalWebServices.findFlightsByCriteria(origin, destination, from, to);
        model.addAttribute("flights", flights);
        
        return "flight-search-results";
    }
}

