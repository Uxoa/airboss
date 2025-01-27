package io.aws.airboss.web.search;

import io.aws.airboss.flights.Flight;
import io.aws.airboss.web.GlobalWebServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class BuscarWebController {
    
    private final GlobalWebServices globalWebServices;
    
    public BuscarWebController(GlobalWebServices globalWebServices) {
        this.globalWebServices = globalWebServices;
    }
    
    @GetMapping("/buscar")
    public String buscar(Model model) {
        // Obtener la lista de aeropuertos desde el servicio
        List<String> airports = globalWebServices.getAllAirportNames()
              .stream()
              .distinct().collect(Collectors.toList());
        // Agregar los datos al modelo
        model.addAttribute("airports", airports);
        model.addAttribute("departureTime", LocalDateTime.now().toString()); // Hora actual
   
        // Retornar la vista correspondiente
        return "buscar/index";
    }
    
    @GetMapping("/buscar/resultados")
    public String searchResults(
          @RequestParam String from,
          @RequestParam String to,
          @RequestParam String date,
          Model model
    ) {
        try {
            // Buscar vuelos que coincidan con los criterios
            List<Flight> flights = globalWebServices.searchFlights(from, to, date);
            
            // Si no hay vuelos, agrega un mensaje
            if (flights.isEmpty()) {
                model.addAttribute("error", "No flights found for the selected criteria.");
            } else {
                model.addAttribute("flights", flights);
            }
            
            // Añadir datos adicionales al modelo
            model.addAttribute("title", "Search Results");
            model.addAttribute("from", from);
            model.addAttribute("to", to);
            model.addAttribute("departureTime", date);
            
            // Renderizar la vista de resultados
            return "buscar/resultados";
        } catch (Exception e) {
            // Manejo de errores
            model.addAttribute("error", "An error occurred while processing your request. Please try again.");
            return "buscar/index"; // Volver a la página de búsqueda en caso de error
        }
    }
    
    
    @GetMapping("/confirmar/{id}")
    public String confirmBooking(@PathVariable Long id, Model model, Principal principal) {
        Optional<Flight> flight = globalWebServices.findFlightById(id);
        model.addAttribute("title", "Confirm Booking");
        model.addAttribute("flight", flight.orElse(null));
        model.addAttribute("username", principal.getName());
        return "buscar/confirmacion";
    }
    
    @PostMapping("/reservas/crear")
    public String createBooking(@RequestParam Long flightId, Principal principal) {
        globalWebServices.createBooking(principal.getName(), flightId);
        return "redirect:/usuario";
    }
}
