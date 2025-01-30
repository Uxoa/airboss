package io.aws.airboss.bookings;

import io.aws.airboss.flights.Flight;
import io.aws.airboss.users.User;

public class BookingDTO {
    private Long bookingId;
    private Long userId;
    private Long flightId;
    private int numberOfSeats;
    
    public BookingDTO(Long bookingId, User user, Flight flight, int numberOfSeats) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.bookingId = bookingId;
        this.userId = user.getUserId();
        this.flightId = flight.getFlightId();
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