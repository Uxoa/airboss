package io.aws.airboss.airports;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/airports")
public class AirportController {
    
    @Autowired
    private AirportService airportService;

	private static final Logger logger = LoggerFactory.getLogger(AirportController.class);
    @Autowired
    private AirportRepository airportRepository;
    
    @GetMapping("/allAirports")
    public @ResponseBody List<Airport> getAllAirports() {
        return airportService.getAllAirports();
    }
    
    @GetMapping("/{iata-code}")
    public @ResponseBody Airport getAirportById(@PathVariable("iata-code") String iataCode) {
        return airportService.getAirportById(iataCode);
    }
    
   

    
   
    
}