package io.aws.airboss.flights;

import io.aws.airboss.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/flights") // 🔹 Eliminamos `/api/`
public class FlightController {
    
    private final IFlightService flightService;
    private final UserService userService;
    
    @Autowired
    public FlightController(IFlightService flightService, UserService userService) {
        this.flightService = flightService;
        this.userService = userService;
    }
    
    // ✅ Buscar vuelos y mostrar la plantilla de resultados
    @GetMapping("/search/resultados") // 🔹 Eliminamos `/api/`
    public String searchFlights(
          @RequestParam String origin,
          @RequestParam String destination,
          @RequestParam String date,
          Model model,
          Principal principal) {
        
        LocalDate departureDate = LocalDate.parse(date);
        List<FlightDTO> flights = flightService.searchFlights(origin, destination, departureDate);
        
        if (flights.isEmpty()) {
            model.addAttribute("error", "No flights found for the selected criteria.");
        } else {
            model.addAttribute("flights", flights);
        }
        
        // 🔹 Obtener `userId` desde el usuario autenticado
        String username = principal.getName();
        Long userId = userService.getUserIdByUsername(username);
        model.addAttribute("userId", userId);
        
        model.addAttribute("title", "Search Results");
        return "results"; // ✅ Asegurar que `results.mustache` se carga correctamente
    }
}
