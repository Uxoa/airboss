package io.aws.airboss.web;

import io.aws.airboss.airports.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.Select;

import java.util.List;
import java.util.Map;

public interface AirportWebRepo extends JpaRepository<Airport, String> {
    
    @Query("SELECT new map(a.name as name, a.iataCode as iataCode) FROM Airport a")
    List<Map<String, String>> getAllAirportNamesAndCodes();
}


