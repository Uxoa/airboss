package io.aws.airboss.airports;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public interface AirportRepository extends JpaRepository<Airport, String> {
    
    @Query("SELECT new map(a.name as name, a.iataCode as iataCode) FROM Airport a")
    List<Map<String, String>> getAllAirportNamesAndCodes();

}