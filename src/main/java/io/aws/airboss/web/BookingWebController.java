package io.aws.airboss.web;

import io.aws.airboss.bookings.Booking;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;
import java.util.Map;

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
    @PostMapping("/reservas/cancel/{id}")
    public String cancelBooking(@PathVariable Long id, Principal principal) {
        globalWebServices.cancelBooking(principal.getName(), id);
        return "redirect:/mybookings";
    }
}

