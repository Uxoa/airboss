package io.aws.airboss.flights;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api/flights")
public class FlightController {
    
    private final IFlightService flightService;
    
    @Autowired
    public FlightController(IFlightService flightService) {
        this.flightService = flightService;
    }
    
    // ✅ Buscar vuelos y mostrar la plantilla de resultados
    @GetMapping("/search/resultados")
    public String searchFlights(
          @RequestParam String origin,
          @RequestParam String destination,
          @RequestParam String date,
          Model model) {
        
        LocalDate departureDate = LocalDate.parse(date);
        List<FlightDTO> flights = flightService.searchFlights(origin, destination, departureDate);
        
        if (flights.isEmpty()) {
            model.addAttribute("error", "No flights found for the selected criteria.");
        } else {
            model.addAttribute("flights", flights);
        }
        
        model.addAttribute("title", "Search Results");
        return "results"; // Renderiza results.mustache
    }
}
