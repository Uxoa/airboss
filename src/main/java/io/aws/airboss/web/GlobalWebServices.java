package io.aws.airboss.web;

import io.aws.airboss.airports.Airport;
import io.aws.airboss.airports.AirportRepository;
import io.aws.airboss.bookings.Booking;
import io.aws.airboss.bookings.BookingRepository;
import io.aws.airboss.flights.Flight;
import io.aws.airboss.roles.Role;
import io.aws.airboss.users.User;
import io.aws.airboss.web.bookings.BookingWebRepository;
import io.aws.airboss.web.flights.FlightWebRepository;
import io.aws.airboss.web.users.ProfileWebRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GlobalWebServices {
    
    private final GlobalWebRepository globalWebRepository;
    private final BookingRepository bookingRepository; // Asegúrate de declarar esta variable correctamente
    private final FlightWebRepository flightWebRepository;
    private final BookingWebRepository bookingWebRepository;
    private final AirportsWeRepository airportsWeRepository;
    private final ProfileWebRepository profileWebRepository;
    private final AirportRepository airportRepository;

    
    @PersistenceContext
    private EntityManager entityManager;
    
    
    // Constructor con los repositorios necesarios
    public GlobalWebServices(GlobalWebRepository globalWebRepository, BookingRepository bookingRepository, FlightWebRepository flightWebRepository, BookingWebRepository bookingWebRepository, AirportsWeRepository airportsWeRepository, ProfileWebRepository profileWebRepository, AirportRepository airportRepository, LocalContainerEntityManagerFactoryBean entityManager) {
        this.globalWebRepository = globalWebRepository;
        this.bookingRepository = bookingRepository; // Inicialización correcta
        this.flightWebRepository = flightWebRepository;
        this.bookingWebRepository = bookingWebRepository;
        this.airportsWeRepository = airportsWeRepository;
        this.profileWebRepository = profileWebRepository;
        this.airportRepository = airportRepository;
        this.entityManager = entityManager.getObject().createEntityManager();
    }
    
    public Optional<User> findUserByUsername(String username) {
        return globalWebRepository.findUserByUsername(username);
    }
    
    public LocalDateTime findLastLogin(String username) {
        return profileWebRepository
              .findLastLoginByUsername(username)
              .orElse(LocalDateTime.MIN); // Usa un valor por defecto
    }
    
    
    
    public Map<String, Object> getUserProfileData(String username) {
        User user = findUserByUsername(username)
              .orElseThrow(() -> new RuntimeException("User not found: " + username));
        
        Map<String, Object> profileData = new HashMap<>();
        profileData.put("username", user.getUsername());
        profileData.put("email", user.getProfile().getEmail());
        profileData.put("role", user.getRoles().stream()
              .map(Role::getName)
              .collect(Collectors.joining(", ")));
        profileData.put("created", user.getProfile().getRegistrationDate());
        profileData.put("lastLogin", findLastLogin(username));
        profileData.put("profileImage", "/images/avatar05.png");
        return profileData;
    }
    
    public List<Booking> getBookingsByUser(String username) {
        return bookingWebRepository.findBookingsByUsername(username);
    }
    
    public List<String> getAllAirportNames() {
        return airportRepository.findAll()
              .stream()
              .map(Airport::getName) // Asegúrate de que `getName` es el campo correcto
              .collect(Collectors.toList());
    }
    
    
    public List<Flight> searchFlights(String from, String to, String date) {
        return entityManager.createQuery(
                    "SELECT f FROM Flight f WHERE f.origin = :from AND f.destination = :to AND f.departureTime = :departureTime",
                    Flight.class
              )
              .setParameter("from", from)
              .setParameter("to", to)
              .setParameter("departureTime", LocalDateTime.parse(date)) // Asegúrate de que el formato sea correcto
              .getResultList();
    }
    
    public Optional<Flight> findFlightById(Long flightId) {
        return  flightWebRepository.findFlightByFlightId(flightId);
    }
    
    public List<Flight> findFlightsByCriteria(String origin, String destination, LocalDateTime fromTime, LocalDateTime toTime) {
        return  flightWebRepository.findFlightsByCriteria(origin, destination, fromTime, toTime);
    }
    
    public void createBooking(String username, Long flightId) {
        // Buscar el usuario por nombre de usuario
        User user = globalWebRepository.findUserByUsername(username)
              .orElseThrow(() -> new RuntimeException("User not found: " + username));
        
        // Buscar el vuelo por ID
        Flight flight =  flightWebRepository.findFlightByFlightId(flightId)
              .orElseThrow(() -> new RuntimeException("Flight not found: " + flightId));
        
        // Crear una nueva reserva
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setFlight(flight);
        
        // Guardar la reserva en el repositorio
        bookingRepository.save(booking);
    }
    
    public void cancelBooking(String username, Long bookingId) {
        // Validar si la reserva pertenece al usuario antes de cancelarla
        Booking booking = bookingRepository.findById(bookingId)
              .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));
        
        if (!booking.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized to cancel booking");
        }
        
        bookingRepository.deleteById(bookingId);
    }
    
    
    

}
