package io.aws.airboss.airports;

import org.springframework.stereotype.Service;

import java.util.List;


public interface AirportService {
    Airport getAirportById(String airportId);
    List<Airport> getAllAirports();
}