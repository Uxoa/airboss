package io.aws.airboss.bookings;

import io.aws.airboss.users.User;
import io.aws.airboss.flights.Flight;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    
    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;
    
    @Column(nullable = false)
    private int numberOfSeats;
    
    @NotNull
    @Column(nullable = false)
    private LocalDateTime bookingDate;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    
    // Constructores
    public Booking() {}
    
    public Booking(User user, Flight flight, int numberOfSeats, LocalDateTime bookingDate, BookingStatus status) {
        this.user = user;
        this.flight = flight;
        this.numberOfSeats = numberOfSeats;
        this.bookingDate = bookingDate;
        this.status = status;
    }
    
    // Getters y Setters
    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }
    
    public int getNumberOfSeats() { return numberOfSeats; }
    public void setNumberOfSeats(int availableSeats) { this.numberOfSeats = availableSeats; }
    
    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }
    
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
}
