package io.aws.airboss.flights;

import io.aws.airboss.flights.Flight;

import java.time.LocalDateTime;

public class FlightDTO {
    private Long flightId;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private int availableSeats;
    private String airlineName;
    
    public FlightDTO(Flight flight) {
        this.flightId = flight.getFlightId();
        this.origin = flight.getOrigin();
        this.destination = flight.getDestination();
        this.departureTime = flight.getDepartureTime();
        this.availableSeats = flight.getAvailableSeats();
        this.airlineName = flight.getAirlineName();
    }
    
    public FlightDTO() {
    }
    
    public Long getFlightId() {
        return flightId;
    }
    
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
    
    public String getOrigin() {
        return origin;
    }
    
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    
    public String getDestination() {
        return destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }
    
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }
    
    public int getAvailableSeats() {
        return availableSeats;
    }
    
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
    
    public String getAirlineName() {
        return airlineName;
    }
    
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }
}
