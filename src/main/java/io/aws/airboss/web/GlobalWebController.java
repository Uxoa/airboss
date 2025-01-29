package io.aws.airboss.web;

import io.aws.airboss.airports.AirportRepository;
import io.aws.airboss.bookings.Booking;
import io.aws.airboss.bookings.BookingServiceImpl;
import io.aws.airboss.flights.Flight;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class GlobalWebController {
    
    private final GlobalWebServices globalWebServices;
    private final AirportWebRepo airportWebRepo;
    private final FlightWebRepository flightWebRepository;
    private final BookingServiceImpl bookingService;
    
    public GlobalWebController(GlobalWebServices globalWebServices, AirportWebRepo airportWebRepo, FlightWebRepository flightWebRepository, GlobalWebRepository globalWebRepository, BookingServiceImpl bookingService) {
        this.globalWebServices = globalWebServices;
        this.airportWebRepo = airportWebRepo;
        this.flightWebRepository = flightWebRepository;
        this.bookingService = bookingService;
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
    
    @GetMapping("/aeropuertos")
    @ResponseBody
    public List<Map<String, String>> getAirports() {
        return airportWebRepo.getAllAirportNamesAndCodes();
    }
    
    @GetMapping(value = "/fechas-disponibles", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, String>> getAvailableDates() {
        return flightWebRepository.findAll().stream()
              .map(flight -> Map.of(
                    "date", flight.getDepartureTime().toLocalDate().toString(),
                    "time", flight.getDepartureTime().toLocalTime().toString()
              ))
              .toList();
    }
    
    /** Reservas del usuario */
    @GetMapping("/mybookings")
    public String userBookings(Model model, Principal principal) {
        model.addAttribute("title", "My Bookings");
        model.addAttribute("bookings", globalWebServices.getBookingsByUser(principal.getName()));
        return "bookings";
    }

}

