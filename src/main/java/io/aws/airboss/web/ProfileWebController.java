package io.aws.airboss.web;

import io.aws.airboss.bookings.Booking;
import io.aws.airboss.users.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ProfileWebController {
    
    private final GlobalWebServices globalWebServices;
    
    public ProfileWebController(GlobalWebServices globalWebServices) {
        this.globalWebServices = globalWebServices;
    }
    
    @GetMapping("/perfil")
    public String profile(Model model, Principal principal) {
        String username = principal.getName();
        
        // Obtener detalles del usuario desde GlobalWebServices
        User user = globalWebServices.getUserDetails(username);
        
        // Obtener los datos del usuario y sus reservas
        String email = globalWebServices.getUserEmail(username);
        String role = globalWebServices.getUserRole(username);
        List<Booking> bookings = globalWebServices.getBookingsByUser(username);
        
        // Agregar los atributos al modelo
        model.addAttribute("title", "Your Profile");
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", email);
        model.addAttribute("avatar", "/images/avatar05.png"); // Imagen predeterminada
        model.addAttribute("role", role);
       //  model.addAttribute("enabled", user.getProfile().isEnabled());
        model.addAttribute("lastLogin", user.getProfile().getLastLogin());
        model.addAttribute("created", user.getProfile().getRegistrationDate());
        model.addAttribute("bookings", bookings);
        
        return "profile";
    }
}
