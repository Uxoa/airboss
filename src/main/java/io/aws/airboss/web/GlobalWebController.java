package io.aws.airboss.web;

import io.aws.airboss.bookings.Booking;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class GlobalWebController {
    
    private final GlobalWebServices globalWebServices;
    
    public GlobalWebController(GlobalWebServices globalWebServices) {
        this.globalWebServices = globalWebServices;
    }
    
    /**
     * Página de bienvenida
     */
    @GetMapping
    public String welcome(Model model) {
        model.addAttribute("title", "Welcome to AirBoss");
        return "welcome";
    }
    
    /**
     * Página de login
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    /**
     * Dashboard
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("title", "Dashboard");
        model.addAttribute("username", principal.getName());
        return "index";
    }
}

