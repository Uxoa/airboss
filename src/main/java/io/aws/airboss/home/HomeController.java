package io.aws.airboss.home;

import java.security.Principal;
import java.util.List;

import io.aws.airboss.bookings.Booking;
import io.aws.airboss.bookings.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
class HomeController {
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("name", "Paloma");
        return "index"; // Renderiza index.ftl
    }
    
  /*  @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    @Autowired
    private BookingService bookingService;
    
    
    @GetMapping("/profile")
    public String profile() {
        return "profile"; // Thymeleaf buscará en /templates/profile.html
    }
    
    @GetMapping("/bookings/view")
    public String bookings(Model model, Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        List<Booking> bookings = bookingService.getBookingsByUserId(userId);
        model.addAttribute("bookings", bookings);
        return "bookings"; // Thymeleaf buscará en /templates/bookings.html
    }*/
    
    
    
    @GetMapping("/private")
    public String connectToPrivatePath(Principal principal) {
        return "Hello, " + principal.getName() + "!";
    }

    @GetMapping("/common")
    public String common() {
        return "Hello, you have read scope!";
    }

}