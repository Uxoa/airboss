package io.aws.airboss.flights;

import io.aws.airboss.airports.Airport;
import io.aws.airboss.airports.AirportRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
    
    @Autowired
    private FlightService flightService;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private AirportRepository airportRepository;
    
    @GetMapping("/api/search")
    public List<Flight> searchFlights(
          @RequestParam String origin,
          @RequestParam String destination,
          @RequestParam String date,
          @RequestParam int seats) {
        
        LocalDateTime departureDate = LocalDateTime.parse(date);
        return flightService.searchFlights(origin, destination, departureDate, seats);
    }
    
    @PutMapping("/{id}/update")
    public void updateFlightAvailability(@PathVariable Long id) {
        flightService.updateFlightAvailability(id);
    }
    
    
    @GetMapping("/available-dates")
    public ResponseEntity<List<Map<@NotNull String, @NotNull String>>> getAvailableFlightDates() {
        List<Flight> flights = flightRepository.findAll(); // Use appropriate query to filter dates if needed.
        List<Map<@org.jetbrains.annotations.NotNull String, @org.jetbrains.annotations.NotNull String>> response = flights.stream()
              .map(flight -> Map.of(
                    "date", flight.getDepartureTime().toLocalDate().toString(),
                    "time", flight.getDepartureTime().toLocalTime().toString()
              ))
              .collect(Collectors.toList()).reversed();
        return ResponseEntity.ok(response);
    }
    
  
}
