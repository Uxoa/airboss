package io.aws.airboss.web;

import io.aws.airboss.flights.Flight;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class BuscarWebController {
    
    private final GlobalWebServices globalWebServices;
    
    public BuscarWebController(GlobalWebServices globalWebServices) {
        this.globalWebServices = globalWebServices;
    }
    
    @GetMapping("/buscar")
    public String searchPage(Model model) {
        model.addAttribute("title", "Search Flights");
        model.addAttribute("airports", globalWebServices.getAllAirports());
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
            List<Flight> flights = globalWebServices.searchFlights(from, to, date);
            model.addAttribute("title", "Search Results");
            model.addAttribute("flights", flights);
            model.addAttribute("from", from);
            model.addAttribute("to", to);
            model.addAttribute("date", date);
            return "buscar/resultados";
        } catch (Exception e) {
            model.addAttribute("error", "Invalid date format or no results found");
            return "buscar/index"; // Devuelve a la página de búsqueda si hay un error
        }
    }
    
    
    @GetMapping("/confirmar/{id}")
    public String confirmBooking(@PathVariable Long id, Model model, Principal principal) {
        Flight flight = globalWebServices.findFlightById(id);
        model.addAttribute("title", "Confirm Booking");
        model.addAttribute("flight", flight);
        model.addAttribute("username", principal.getName());
        return "buscar/confirmacion";
    }
    
    @PostMapping("/reservas/crear")
    public String createBooking(@RequestParam Long flightId, Principal principal) {
        globalWebServices.createBooking(principal.getName(), flightId);
        return "redirect:/perfil";
    }
}
