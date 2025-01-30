package io.aws.airboss.flights;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements IFlightService {
    
    private final FlightRepository flightRepository;
    
    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }
    
    @Override
    public Flight getFlightById(Long flightId) {
        return flightRepository.findById(flightId)
              .orElseThrow(() -> new RuntimeException("Flight not found with ID: " + flightId));
    }
    @Override
    public List<FlightDTO> searchFlights(String origin, String destination, LocalDate date) {
        return flightRepository.findByOriginAndDestinationAndDepartureTimeAfterAndAvailableSeatsGreaterThan(
              origin, destination, date.atStartOfDay(), 0
        ).stream().map(FlightDTO::new).collect(Collectors.toList());
    }
    
    @Override
    public List<String> getAvailableFlightDates() {
        return flightRepository.findAll().stream()
              .map(flight -> flight.getDepartureTime().toLocalDate().toString())
              .distinct()
              .collect(Collectors.toList());
    }
}
