package io.aws.airboss.web;

import io.aws.airboss.bookings.Booking;
import io.aws.airboss.flights.Flight;
import io.aws.airboss.users.User;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class GlobalWebRepositoryImpl implements GlobalWebRepository {
    
    @PersistenceContext
    private EntityManager entityManager; // Inyecta automáticamente el EntityManager
    
    private final WebUserRepository userRepository;
    private final WebBookingRepository bookingRepository;
    
    public GlobalWebRepositoryImpl(WebUserRepository userRepository, WebBookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }
    
    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
    public List<Booking> findBookingsByUsername(String username) {
        return bookingRepository.findAllByUsername(username);
    }
    
    @Override
    public void cancelBooking(String username, Long bookingId) {
        String ownerUsername = findUsernameByBookingId(bookingId)
              .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));
        
        if (!ownerUsername.equals(username)) {
            throw new RuntimeException("Unauthorized to cancel this booking");
        }
        
        bookingRepository.deleteById(bookingId);
    }

    
    @Override
    public List<String> getAllAirports() {
        return entityManager.createQuery(
              "SELECT DISTINCT f.origin FROM Flight f UNION SELECT DISTINCT f.destination FROM Flight f",
              String.class
        ).getResultList();
    }
    
    @Override
    public List<Flight> searchFlights(String from, String to, String date) {
        return entityManager.createQuery(
                    "SELECT f FROM Flight f WHERE f.origin = :from AND f.destination = :to AND f.date = :date",
                    Flight.class
              )
              .setParameter("from", from)
              .setParameter("to", to)
              .setParameter("date", LocalDate.parse(date))
              .getResultList();
    }
    
    @Override
    public void createBooking(String username, Long flightId) {
        User user = userRepository.findByUsername(username)
              .orElseThrow(() -> new RuntimeException("User not found: " + username));
        Flight flight = entityManager.find(Flight.class, flightId);
        if (flight == null) {
            throw new RuntimeException("Flight not found: " + flightId);
        }
        
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setFlight(flight);
        entityManager.persist(booking);
    }
    
    @Override
    public Optional<Flight> findFlightById(Long flightId) {
        return Optional.ofNullable(entityManager.find(Flight.class, flightId));
    }
    
    @Override
    public Optional<String> findUsernameByBookingId(Long bookingId) {
        return entityManager.createQuery(
                    "SELECT b.user.username FROM Booking b WHERE b.id = :bookingId",
                    String.class
              )
              .setParameter("bookingId", bookingId)
              .getResultStream()
              .findFirst();
    }
  
   
}
