package io.aws.airboss.bookings;

import java.time.LocalDateTime;

public class BookingDTO {
    private Long bookingId;
    private Long userId;
    private String username;
    private Long flightId;
    private int numberOfSeats;
    private String origin;
    private String destination;
    private String bookingStatus;
    private LocalDateTime bookingDate;
    
    
    // Constructor with parameters
    public BookingDTO(Long bookingId, Long userId, String username, Long flightId, int numberOfSeats, String origin, String destination,
                      String bookingStatus, LocalDateTime bookingDate) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.username = username;
        this.flightId = flightId;
        this.numberOfSeats = numberOfSeats;
        this.origin = origin;
        this.destination = destination;
        this.bookingStatus = bookingStatus;
        this.bookingDate = bookingDate;
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
    
    public int getNumberOfSeats() {
        return numberOfSeats;
    }
    
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
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
    
    public String getBookingStatus() {
        return bookingStatus;
    }
    
    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    
    public LocalDateTime getBookingDate() {
        return bookingDate;
    }
    
    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }
}