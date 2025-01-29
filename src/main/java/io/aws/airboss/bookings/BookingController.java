package io.aws.airboss.bookings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // ⬅️ Cambiado de @RestController a @Controller para renderizar Mustache
@RequestMapping("/api/bookings")
public class BookingController {
    
    private final IBookingService bookingService;
    
    @Autowired
    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }
    
    // ✅ Crear una reserva y redirigir a confirmacion.mustache
    @PostMapping("/create")
    public String createBooking(@RequestBody BookingRequestDTO request, Model model) {
        Booking booking = bookingService.createBooking(request.getUserId(), request.getFlightId(), request.getAvailableSeats());
        
        if (booking == null) {
            model.addAttribute("error", "Error processing booking.");
            return "error"; // Redirige a la página de error si falla
        }
        
        model.addAttribute("bookingId", booking.getBookingId());
        return "redirect:/confirmacion?bookingId=" + booking.getBookingId(); // Redirigir a la confirmación
    }
    
    // ✅ Mostrar la página de confirmación con detalles de la reserva
    @GetMapping("/confirmacion")
    public String showConfirmation(@RequestParam Long bookingId, Model model) {
        Booking booking = bookingService.getBookingById(bookingId);
        
        if (booking == null) {
            model.addAttribute("error", "Booking not found.");
            return "error"; // Redirige a una plantilla de error si la reserva no existe
        }
        
        model.addAttribute("bookingId", booking.getBookingId());
        model.addAttribute("flightId", booking.getFlight().getFlightId());
        model.addAttribute("origin", booking.getFlight().getOrigin());
        model.addAttribute("destination", booking.getFlight().getDestination());
        model.addAttribute("departureTime", booking.getFlight().getDepartureTime());
        model.addAttribute("username", booking.getUser().getUsername());
        model.addAttribute("numberOfSeats", booking.getNumberOfSeats());
        
        return "confirm"; // Renderiza confirm.mustache
    }
}
