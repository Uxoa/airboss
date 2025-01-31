package io.aws.airboss.flights;

import java.time.LocalDate;
import java.util.List;

public interface IFlightService {
    List<FlightDTO> searchFlights(String origin, String destination, LocalDate date);
    List<String> getAvailableFlightDates();
    Flight getFlightById(Long flightId);
    
    Flight createFlight(Flight flight);
    
    Flight updateFlight(Long id, Flight flight);
    
    void deleteFlight(Long id);
}
