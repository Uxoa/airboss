package io.aws.airboss.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlobalWebError {
    
    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Lógica para manejar errores
        model.addAttribute("message", "Something went wrong. Please try again.");
        return "error"; // Renderiza la plantilla error.html
    }
}
