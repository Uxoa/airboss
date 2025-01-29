package io.aws.airboss.flights;

import io.aws.airboss.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller  // ❌ No maneja vistas Mustache, solo JSON
@RequestMapping("/api/flights")
public class FlightController {
    
    private final IFlightService IFlightService;
    private final UserService userService;
    
    @Autowired
    public FlightController(IFlightService IFlightService, UserService userService) {
        this.IFlightService = IFlightService;
        this.userService = userService;
    }
    
    // ✅ Modificar para retornar una plantilla Mustache
    @GetMapping("/search/resultados")
    public String searchFlights(
          @RequestParam String origin,
          @RequestParam String destination,
          @RequestParam String date,
          Model model,
          Principal principal) { // Obtener el usuario autenticado
        
        LocalDate departureDate = LocalDate.parse(date);
        List<FlightDTO> flights = IFlightService.searchFlights(origin, destination, departureDate);
        
        if (flights.isEmpty()) {
            model.addAttribute("error", "No flights found for the selected criteria.");
        } else {
            model.addAttribute("flights", flights);
        }
        
        // Obtener userId del usuario autenticado y pasarlo a la plantilla
        String username = principal.getName();
        Long userId = userService.getUserIdByUsername(username);
        model.addAttribute("userId", userId);
        
        model.addAttribute("title", "Search Results");
        
        return "resultados";
    }
    
    
    
    // ✅ API para obtener fechas disponibles
    @GetMapping("/available-dates")
    public ResponseEntity<List<String>> getAvailableFlightDates() {
        List<String> availableDates = IFlightService.getAvailableFlightDates();
        return ResponseEntity.ok(availableDates);
    }
    
}
