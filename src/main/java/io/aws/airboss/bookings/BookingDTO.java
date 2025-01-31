package io.aws.airboss.bookings;

import java.time.LocalDateTime;

public class BookingDTO {
    private Long bookingId;
    private Long userId;
    private String username;
    private Long flightId;
    private String airlineName;
    private int availableSeats;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private int numberOfSeats;
    private LocalDateTime bookingDate;
    private BookingStatus bookingStatus;

    
    // Constructor
    public BookingDTO() {
    }
    
    // Constructor with parameters
    public BookingDTO(Long bookingId, Long userId, String username, Long flightId,
                      String airlineName, int availableSeats, String origin, String destination,
                      LocalDateTime departureTime, int numberOfSeats,BookingStatus Status,
                      LocalDateTime bookingDate) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.username = username;
        this.flightId = flightId;
        this.airlineName = airlineName;
        this.availableSeats = availableSeats;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.numberOfSeats = numberOfSeats;
        this.bookingDate = bookingDate;
        this.bookingStatus = Status;
    }
    
    
    // Getters and setters
    
    
    public Long getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public Long getFlightId() {
        return flightId;
    }
    
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
    
    public String getAirlineName() {
        return airlineName;
    }
    
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }
    
    public int getAvailableSeats() {
        return availableSeats;
    }
    
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
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
    
    public int getNumberOfSeats() {
        return numberOfSeats;
    }
    
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }
    
    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }
    
    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }
    
    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}