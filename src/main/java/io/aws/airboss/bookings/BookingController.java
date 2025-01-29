package io.aws.airboss.bookings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // ✅ Cambiado a @Controller para usar plantillas Mustache
@RequestMapping("/api/bookings")
public class BookingController {
    
    private final IBookingService bookingService;
    private final BookingRepository bookingRepository;
    
    @Autowired
    public BookingController(IBookingService bookingService, BookingRepository bookingRepository) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
    }
    
    // ✅ CREAR RESERVA Y REDIRIGIR A LA CONFIRMACIÓN
    @PostMapping("/create")
    public String createBooking(@RequestBody BookingRequestDTO request) {
        Booking booking = bookingService.createBooking(request.getUserId(), request.getFlightId(), request.getAvailableSeats());
        
        if (booking == null) {
            return "redirect:/error?message=Error processing booking"; // ✅ Redirige a error.mustache si falla
        }
        
        return "redirect:/reservas/confirmacion?bookingId=" + booking.getBookingId(); // ✅ Redirige a confirmación
    }
    
    // ✅ MOSTRAR CONFIRMACIÓN DE RESERVA
    @GetMapping("/confirmacion")
    public String showConfirmation(@RequestParam Long bookingId, Model model) {
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            return "redirect:/error?message=Booking not found"; // ✅ Manejo de error si no se encuentra la reserva
        }
        
        model.addAttribute("booking", booking);
        return "confirmacion"; // ✅ Renderiza `confirmacion.mustache`
    }
    
    // ✅ OBTENER TODAS LAS RESERVAS
    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }
    
    // ✅ OBTENER RESERVA POR ID
    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(booking);
    }
    
    // ✅ CONFIRMAR RESERVA
    @PostMapping("/confirm/{bookingId}")
    public ResponseEntity<String> confirmBooking(@PathVariable Long bookingId) {
        bookingService.confirmBooking(bookingId);
        return ResponseEntity.ok("/reservas/confirmacion?bookingId=" + bookingId);
    }
    
    // ✅ CANCELAR RESERVA
    @PostMapping("/cancel/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
