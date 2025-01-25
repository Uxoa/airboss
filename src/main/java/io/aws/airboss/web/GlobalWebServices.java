package io.aws.airboss.web;

import io.aws.airboss.bookings.Booking;
import io.aws.airboss.flights.Flight;
import io.aws.airboss.profiles.Profile;
import io.aws.airboss.roles.Role;
import io.aws.airboss.roles.RoleService;
import io.aws.airboss.users.User;
import io.aws.airboss.users.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GlobalWebServices {
    
    private final GlobalWebRepository globalWebRepository;
    private final WebUserRepository webUserRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final WebBookingRepository webBookingRepository;
    
    public GlobalWebServices(GlobalWebRepository globalWebRepository, WebUserRepository webUserRepository, UserMapper userMapper, RoleService roleService, WebBookingRepository webBookingRepository) {
        this.globalWebRepository = globalWebRepository;
        this.webUserRepository = webUserRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.webBookingRepository = webBookingRepository;
    }
    
    public User getUserDetails(String username) {
        return globalWebRepository.findUserByUsername(username)
              .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
    
    public String getUserEmail(String username) {
        return webUserRepository.findByUsername(username)
              .map(user -> user.getProfile().getEmail()) // Accede al perfil del usuario
              .orElseThrow(() -> new RuntimeException("User or Profile not found: " + username));
    }
    
    
    public String getUserRole(String username) {
        User user = getUserDetails(username);
        
        return Optional.ofNullable(user.getRoles())
              .map(roles -> roles.stream()
                    .map(Role::getName)
                    .collect(Collectors.joining(", ")))
              .orElse("No roles assigned");
    }
    
    public List<Booking> getBookingsByUser(String username) {
        return globalWebRepository.findBookingsByUsername(username);
    }

    
    public void cancelBooking(String username, Long bookingId) {
        globalWebRepository.cancelBooking(username, bookingId);
    }
    
    public List<Booking> getBookingsByUsername(String username) {
        return webBookingRepository.findAllByUsername(username);
    }
    
    public List<String> getAllAirports() {
        return globalWebRepository.getAllAirports();
    }
    
    public List<Flight> searchFlights(String from, String to, String date) {
        return globalWebRepository.searchFlights(from, to, date);
    }
    
    public void createBooking(String username, Long flightId) {
        globalWebRepository.createBooking(username, flightId);
    }
    
    public Flight findFlightById(Long flightId) {
        return globalWebRepository.findFlightById(flightId)
              .orElseThrow(() -> new RuntimeException("Flight not found: " + flightId));
    }
    
    
}
