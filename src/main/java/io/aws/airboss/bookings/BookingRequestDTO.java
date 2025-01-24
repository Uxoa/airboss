package io.aws.airboss.bookings;

public class BookingRequestDTO {
    private Long userId;
    private Long flightId;
    private int numberOfSeats;
    
    public BookingRequestDTO() {
    }
    
    // Getters y setters
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
