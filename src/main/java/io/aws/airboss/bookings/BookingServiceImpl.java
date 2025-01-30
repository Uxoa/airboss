package io.aws.airboss.bookings;

import io.aws.airboss.bookings.exceptions.NoAvailableSeatsException;
import io.aws.airboss.flights.Flight;
import io.aws.airboss.flights.FlightRepository;
import io.aws.airboss.users.User;
import io.aws.airboss.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BookingServiceImpl implements IBookingService {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private FlightRepository flightRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    private final ConcurrentHashMap<Long, LocalDateTime> seatHoldMap = new ConcurrentHashMap<>();
    
    @Override
    public Booking createBooking(Long userId, Long flightId, int availableSeats) {
        Flight flight = flightRepository.findById(flightId)
              .orElseThrow(() -> new RuntimeException("Flight not found"));
        
        if (flight.getAvailableSeats() < availableSeats) {
            throw new RuntimeException("Not enough available seats");
        }
        
        Booking booking = new Booking();
        booking.setUser(userRepository.findById(userId)
              .orElseThrow(() -> new RuntimeException("User not found")));
        booking.setFlight(flight);
        booking.setNumberOfSeats(availableSeats);
        booking.setStatus(BookingStatus.CREATED);
        booking.setBookingDate(LocalDateTime.now());
        
        Booking savedBooking = bookingRepository.save(booking);
        System.out.println("🚀 Booking creada con ID: " + savedBooking.getBookingId());
        
        flight.setAvailableSeats(flight.getAvailableSeats() - availableSeats);
        flightRepository.save(flight);
        
        return savedBooking;
    }
    
    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }
    
    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    
    @Override
    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
              .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
    }
    
    @Override
    @Transactional
    public void confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
              .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);
        seatHoldMap.remove(booking.getFlight().getFlightId());
    }
    
    @Override
    @Transactional
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
              .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        
        Flight flight = booking.getFlight();
        
        synchronized (this) {
            flight.setAvailableSeats(flight.getAvailableSeats() + booking.getNumberOfSeats());
            flightRepository.save(flight);
        }
        
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
}