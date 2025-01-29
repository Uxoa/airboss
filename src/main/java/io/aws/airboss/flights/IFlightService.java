package io.aws.airboss.flights;

import java.time.LocalDate;
import java.util.List;

public interface IFlightService {
    List<FlightDTO> searchFlights(String origin, String destination, LocalDate date);
    List<String> getAvailableFlightDates();
}
