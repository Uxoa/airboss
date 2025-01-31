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
    public Booking createBooking(
          Long userId, Long flightId, int numberOfSeats, String bookingStatus,
          LocalDateTime bookingDate, String origin, String destination,
          LocalDateTime departureDate, String airlineName, int availableSeats,
          String username) {
        
        User user = userRepository.findById(userId)
              .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Flight flight = flightRepository.findById(flightId)
              .orElseThrow(() -> new RuntimeException("Vuelo no encontrado"));
        
        if (flight.getAvailableSeats() < numberOfSeats) {
            throw new NoAvailableSeatsException("No hay suficientes asientos disponibles");
        }
        
        synchronized (this) {
            flight.setAvailableSeats(flight.getAvailableSeats() - numberOfSeats);
            flightRepository.save(flight);
        }
        
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setFlight(flight);
        booking.setNumberOfSeats(numberOfSeats);
        booking.setStatus(BookingStatus.PENDING);
        booking.setBookingDate(bookingDate);
        
        return bookingRepository.save(booking);
    }
    
    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }
    
    @Override
    public Booking updateBooking(Long id, Booking booking) {
        return null;
    }
    
    @Override
    public void deleteBooking(Long id) {
    
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