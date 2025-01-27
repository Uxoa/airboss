package io.aws.airboss.web;

import io.aws.airboss.airports.AirportRepository;
import io.aws.airboss.web.flights.FlightWebRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class GlobalWebController {
    
    private final GlobalWebServices globalWebServices;
    private final AirportRepository airportRepository;
    private final FlightWebRepository flightWebRepository;
    
    public GlobalWebController(GlobalWebServices globalWebServices, AirportRepository airportRepository, FlightWebRepository flightWebRepository) {
        this.globalWebServices = globalWebServices;
        this.airportRepository = airportRepository;
        this.flightWebRepository = flightWebRepository;
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
    public List<Map<String, String>> getAirports() {
        return airportRepository.findAll().stream()
              .map(airport -> {
                  Map<String, String> map = new HashMap<>();
                  map.put("name", airport.getName());
                  map.put("iataCode", airport.getIataCode());
                  return map;
              })
              .toList();
    }
    
    
    @GetMapping("/fechas-disponibles")
    public List<Map<String, String>> getAvailableDates() {
        return flightWebRepository.findAll().stream()
              .map(flight -> Map.of(
                    "date", flight.getDepartureTime().toLocalDate().toString(),
                    "time", flight.getDepartureTime().toLocalTime().toString()
              ))
              .toList();
    }
    
}

