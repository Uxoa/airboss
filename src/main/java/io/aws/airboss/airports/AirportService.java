package io.aws.airboss.airports;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AirportService extends JpaRepository<Airport, Long> {
    Airport getAirportById(String airportId);
    List<Airport> getAllAirports();
    Airport createAirport(Airport airport);
    Airport updateAirport(Long id, Airport airport);
    void deleteAirport(Long id);
}