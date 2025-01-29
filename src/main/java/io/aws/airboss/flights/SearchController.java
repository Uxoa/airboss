package io.aws.airboss.flights;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  // ✅ Maneja la vista Mustache
@RequestMapping("/search")
public class SearchController {
    
    @GetMapping
    public String showSearchPage(Model model) {
        model.addAttribute("title", "Search Flights");
        return "search";  // Apunta a src/main/resources/templates/search/search.mustache
    }
}
