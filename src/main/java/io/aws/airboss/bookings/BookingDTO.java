package io.aws.airboss.bookings;

public class BookingDTO {
    private Long bookingId;
    private Long userId;
    private Long flightId;
    private int numberOfSeats;
    
    // Constructor with parameters
    public BookingDTO(Long bookingId, Long userId, Long flightId, int numberOfSeats) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.flightId = flightId;
        this.numberOfSeats = numberOfSeats;
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
}