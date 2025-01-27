package io.aws.airboss.web.users;

import io.aws.airboss.flights.Flight;
import io.aws.airboss.web.GlobalWebServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ProfileWebController {
    
    private final GlobalWebServices globalWebServices;
    
    public ProfileWebController(GlobalWebServices globalWebServices) {
        this.globalWebServices = globalWebServices;
    }
    
    @GetMapping("/perfil")
    public String profile(Model model, Principal principal) {
        String username = principal.getName();
        Map<String, Object> profileData = globalWebServices.getUserProfileData(username);
        
        List<Map<String, Object>> bookings = globalWebServices.getBookingsByUser(username).stream()
              .map(booking -> {
                  Map<String, Object> bookingData = new HashMap<>();
                  Flight flight = booking.getFlight();
                  bookingData.put("flightDetails", String.format("Flight %s: %s -> %s, Date: %s",
                        flight.getFlightId(),
                        flight.getOrigin(),
                        flight.getDestination(),
                        flight.getDepartureTime()));
                  return bookingData;
              })
              .collect(Collectors.toList());
        
        model.addAttribute("title", "Your Profile");
        model.addAttribute("username", profileData.get("username"));
        model.addAttribute("email", profileData.get("email"));
        model.addAttribute("role", profileData.get("role"));
        model.addAttribute("created", profileData.get("created"));
        model.addAttribute("lastLogin", profileData.get("lastLogin"));
        model.addAttribute("bookings", bookings);
        model.addAttribute("profileImage", profileData.get("profileImage"));
        model.addAttribute("enabled", profileData.get("enabled"));
        
        return "perfil/index";
    }
}
