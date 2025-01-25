package io.aws.airboss.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlobalWebError {
    
    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute("javax.servlet.error.status_code");
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("message", "Error Code: " + statusCode);
        } else {
            model.addAttribute("message", "An unexpected error occurred.");
        }
        return "error";
    }
    
    
}
