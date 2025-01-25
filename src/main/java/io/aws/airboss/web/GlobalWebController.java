package io.aws.airboss.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class GlobalWebController {
    
    private final GlobalWebServices globalWebServices;
    
    public GlobalWebController(GlobalWebServices globalWebServices) {
        this.globalWebServices = globalWebServices;
    }
    
    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("title", "Welcome to AirBoss");
        return "welcome";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("title", "Dashboard");
        model.addAttribute("username", principal.getName());
        return "index";
    }
    
    @GetMapping("/perfil")
    public String profile(Model model, Principal principal) {
        model.addAttribute("title", "Your Profile");
        model.addAttribute("username", principal.getName());
        model.addAttribute("avatar", "/images/avatar05.png");
        return "profile";
    }
    
    @GetMapping("/buscar")
    public String search(@RequestParam(required = false) String query, Model model) {
        model.addAttribute("title", "Search Flights");
        model.addAttribute("query", query);
        model.addAttribute("results", query != null ? globalWebServices.searchFlights(query) : null);
        return "search";
    }
    
    @GetMapping("/mybookings")
    public String bookings(Model model, Principal principal) {
        model.addAttribute("title", "My Bookings");
        model.addAttribute("bookings", globalWebServices.getBookingsByUser(principal.getName()));
        return "bookings";
    }
    
    @GetMapping("/details/{id}")
    public String bookingDetails(@PathVariable Long id, Model model) {
        model.addAttribute("title", "Booking Details");
        model.addAttribute("details", globalWebServices.getBookingDetails(id));
        return "details";
    }
    
    @GetMapping("/vuelos")
    public String flights(Model model) {
        model.addAttribute("title", "Available Flights");
        model.addAttribute("flights", globalWebServices.getAvailableFlights());
        return "flights";
    }
    
    @GetMapping("/vuelos/{id}")
    public String flightDetails(@PathVariable Long id, Model model) {
        model.addAttribute("title", "Flight Details");
        model.addAttribute("details", globalWebServices.getFlightDetails(id));
        return "flights/detail";
    }
    
    @GetMapping("/reservar/create")
    public String createBookingForm(Model model) {
        model.addAttribute("title", "Create Booking");
        model.addAttribute("flights", globalWebServices.getAvailableFlights());
        return "bookings/create";
    }
    
    @PostMapping("/reservas/confirm")
    public String createBooking(@RequestParam Long flightId, @RequestParam int numberOfSeats, Principal principal) {
        globalWebServices.createBooking(principal.getName(), flightId, numberOfSeats);
        return "redirect:/mybookings";
    }
    
    @PostMapping("/reservas/cancel/{id}")
    public String cancelBooking(@PathVariable Long id, Principal principal) {
        globalWebServices.cancelBooking(principal.getName(), id);
        return "redirect:/mybookings";
    }
}
