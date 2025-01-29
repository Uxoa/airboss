package io.aws.airboss.web;

import io.aws.airboss.airports.Airport;
import io.aws.airboss.airports.AirportRepository;
import io.aws.airboss.bookings.Booking;
import io.aws.airboss.bookings.BookingRepository;
import io.aws.airboss.flights.Flight;
import io.aws.airboss.roles.Role;
import io.aws.airboss.users.User;
import io.aws.airboss.users.UserRepository;
import io.aws.airboss.web.users.ProfileWebRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GlobalWebServices {
    
    private final GlobalWebRepository globalWebRepository;
    private final BookingRepository bookingRepository; // Asegúrate de declarar esta variable correctamente
    private final FlightWebRepository flightWebRepository;
    private final BookingWebRepository bookingWebRepository;
    private final ProfileWebRepository profileWebRepository;
    private final AirportRepository airportRepository;
    private final UserRepository userRepository;
    
    
    @PersistenceContext
    private EntityManager entityManager;
    
    
    // Constructor con los repositorios necesarios
    public GlobalWebServices(GlobalWebRepository globalWebRepository, BookingRepository bookingRepository, FlightWebRepository flightWebRepository, BookingWebRepository bookingWebRepository, ProfileWebRepository profileWebRepository, AirportRepository airportRepository, UserRepository userRepository) {
        this.globalWebRepository = globalWebRepository;
        this.bookingRepository = bookingRepository;
        this.flightWebRepository = flightWebRepository;
        this.bookingWebRepository = bookingWebRepository;
        this.profileWebRepository = profileWebRepository;
        this.airportRepository = airportRepository;
        this.userRepository = userRepository;
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
        LocalDateTime parsedDate = LocalDate.parse(date).atStartOfDay(); // Convertir fecha a LocalDateTime
        LocalDateTime endOfDay = parsedDate.plusDays(1); // Fin del día seleccionado
        
        // Buscar vuelos que coincidan con origen, destino y fecha
        return flightWebRepository.findFlightsByCriteria(from, to, parsedDate, endOfDay);
    }
    
    public Optional<Flight> findFlightById(Long flightId) {
        return  flightWebRepository.findFlightByFlightId(flightId);
    }
    
    public List<Flight> findFlightsByCriteria(String origin, String destination, LocalDateTime fromTime, LocalDateTime toTime) {
        return  flightWebRepository.findFlightsByCriteria(origin, destination, fromTime, toTime);
    }
    
  /*  public Booking createBooking(String username, Long flightId) {
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
        return booking;
    }*/
  public Long getUserIdByUsername(String username) {
      User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
      return user.getUserId(); // Devuelve el ID del usuario
  }
  
    
    public List<Flight> findSuggestedFlights(String from, String to, LocalDate departureDate) {
        LocalDateTime startOfDay = departureDate.atStartOfDay();
        LocalDateTime endOfDay = departureDate.atTime(LocalTime.MAX);
        LocalDateTime exactTime = LocalDateTime.of(departureDate, LocalTime.NOON);
        
        List<Flight> suggestedFlights = flightWebRepository.findFlightsBySameDayOtherTimes(from, to, startOfDay, endOfDay, exactTime);
        System.out.println("Suggested Flights: " + suggestedFlights); // Debug log
        return suggestedFlights;
    }
    
    
    
    
}
