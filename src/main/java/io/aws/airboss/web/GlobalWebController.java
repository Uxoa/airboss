package io.aws.airboss.web;

import io.aws.airboss.airports.AirportRepository;
import io.aws.airboss.bookings.Booking;
import io.aws.airboss.bookings.BookingService;
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
    private final BookingService bookingService;
    private final GlobalWebRepository globalWebRepository;
    
    public GlobalWebController(GlobalWebServices globalWebServices, AirportWebRepo airportWebRepo, FlightWebRepository flightWebRepository, BookingService bookingService, GlobalWebRepository globalWebRepository) {
        this.globalWebServices = globalWebServices;
        this.airportWebRepo = airportWebRepo;
        this.flightWebRepository = flightWebRepository;
        this.bookingService = bookingService;
        this.globalWebRepository = globalWebRepository;
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
    
    
    
    @GetMapping("/flights/search")
    public String getFlights(
          @RequestParam(required = false) String from,
          @RequestParam(required = false) String to,
          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
          Model model) {
        
        List<Flight> flights = globalWebServices.searchFlights(from, to, date.toString());
        
        if (flights.isEmpty()) {
            // Si no hay vuelos, agregamos vuelos sugeridos como ejemplo
            List<Flight> suggestedFlights = globalWebServices.findFlightsByCriteria("MEX", "CUN", LocalDateTime.now(), LocalDateTime.now().plusDays(1));
            model.addAttribute("suggestedFlights", suggestedFlights);
        } else {
            model.addAttribute("flights", flights);
        }
        
        return "buscar/resultados";
    }
    
    @GetMapping("/buscar")
    public String buscarVuelos(Model model) {
        model.addAttribute("title", "Buscar Vuelos");
        return "buscar/index"; // Renderiza la plantilla buscar/buscar.html o buscar.mustache
    }
    
    @GetMapping("/buscar/resultados")
    public String buscarResultados(
          @RequestParam String from,
          @RequestParam String to,
          @RequestParam String date,
          Model model
    ) {
        // Buscar vuelos
        List<Flight> flights = flightWebRepository.findByOriginAndDestinationAndDepartureDate(
              from, to, LocalDate.parse(date)
        );
        
        if (flights.isEmpty()) {
            model.addAttribute("error", "No flights found for the selected criteria.");
        } else {
            model.addAttribute("flights", flights);
        }
        
        // Pasar información a la vista
        model.addAttribute("title", "Search Results");
        return "buscar/resultados"; // Renderiza la plantilla buscar/resultados.html o resultados.mustache
    }
    
    
    @PostMapping("/reservas/crear")
    public String createBooking(
          @RequestParam Long flightId,
          @RequestParam Integer numberOfSeats,
          Principal principal
    ) {
        String username = principal.getName();
        Long userId = globalWebServices.getUserIdByUsername(username);
        bookingService.createBooking(userId, flightId, numberOfSeats);
        return "redirect:/reservas/view";
    }
    
    @GetMapping("/reservas/view")
    public String viewReservations(Model model, Principal principal) {
        String username = principal.getName();
        List<Booking> bookings = globalWebServices.getBookingsByUser(username);
        
        model.addAttribute("title", "Mis Reservas");
        model.addAttribute("bookings", bookings); // La vista ahora manejará los mapas directamente
        return "reservas/view";
    }
    
    
    /** Reservas del usuario */
    @GetMapping("/mybookings")
    public String userBookings(Model model, Principal principal) {
        model.addAttribute("title", "My Bookings");
        model.addAttribute("bookings", globalWebServices.getBookingsByUser(principal.getName()));
        return "bookings";
    }
    
    
    /** Cancelar una reserva */
    @PostMapping("/reservas/cancelar/{id}")
    public String cancelBooking(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        globalWebServices.cancelBooking(username, id);
        return "redirect:/usuario"; // Redirigir a la página de perfil después de cancelar
    }
    
    
    
    @GetMapping("/reservas/crear/{id}")
    public String showCreateReservationForm(@PathVariable Long id, Principal principal, Model model) {
        try {
            // Obtén el usuario autenticado
            String username = principal.getName();
            
            // Busca los detalles del vuelo
            Flight flight = globalWebServices.findFlightById(id)
                  .orElseThrow(() -> new RuntimeException("Flight not found: " + id));
            
            // Agrega los datos al modelo
            model.addAttribute("userId", username);
            model.addAttribute("flight", flight);
            
            return "reservas/create";
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo cargar la información del vuelo: " + e.getMessage());
            return "buscar/resultados"; // Redirige a los resultados en caso de error
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
    
}

