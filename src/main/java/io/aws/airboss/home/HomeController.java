package io.aws.airboss.home;

import io.aws.airboss.bookings.Booking;
import io.aws.airboss.bookings.BookingService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    
    private final BookingService bookingService;
    
    public HomeController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    
    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("title", "Welcome to AirBoss");
        return "welcome"; // Página pública de bienvenida
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("title", "Dashboard");
        model.addAttribute("username", principal.getName());
        return "index"; // Página privada principal
    }
    
    @GetMapping("/profile")
    public String profile() {
        return "profile"; // Página de perfil del usuario autenticado
    }
    
    @GetMapping("/bookings/view")
    public String bookings(Model model, Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName()); // ID del usuario
        List<Booking> bookings = bookingService.getBookingsByUserId(userId);
        model.addAttribute("bookings", bookings);
        return "bookings"; // Página de reservas del usuario
    }

}
