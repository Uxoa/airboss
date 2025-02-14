package io.aws.airboss.flights;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
public class Flight {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private Long flightId;
    
    @Column(nullable = false)
    private String origin;
    
    @Column(nullable = false)
    private String destination;
    
    @Column(nullable = false, name = "departure_time")
    private LocalDateTime departureTime;
    
    @Column(nullable = false, name = "total_seats")
    private int totalSeats;
    
    @Column(nullable = false, name = "available_seats")
    private int availableSeats;
    
    @Column(nullable = false, name = "is_available")
    private boolean isAvailable;
    
    @Column(nullable = false, name = "airline_name")
    private String airlineName;
    

    
    public Flight() {}
    
    public Flight(String origin, String destination, LocalDateTime departureTime, int totalSeats,
                  int availableSeats, boolean isAvailable, String airlineName) {
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.isAvailable = isAvailable;
        this.airlineName = airlineName;
        
    }
    
    public Flight(String origin, String destination, String departureTime, int totalSeats, int availableSeats, boolean isAvailable, String airlineName) {
        this.origin = origin;
        this.destination = destination;
        this.departureTime = LocalDateTime.parse(departureTime);
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.isAvailable = isAvailable;
        this.airlineName = airlineName;
    }
    
    // Getters y setters
    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }
    
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
    
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    
    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }
    
    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }
    
    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
    
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    
    public String getAirlineName() { return airlineName; }
    public void setAirlineName(String airlineName) { this.airlineName = airlineName; }
    
    
    @Override
    public String toString() {
        return "Flight{" +
              "flightId=" + flightId +
              ", origin='" + origin + '\'' +
              ", destination='" + destination + '\'' +
              ", departureTime=" + departureTime +
              ", totalSeats=" + totalSeats +
              ", availableSeats=" + availableSeats +
              ", isAvailable=" + isAvailable +
              ", airlineName='" + airlineName + '\'' +
              '}';
    }
    
}
