package org.factoriaf5.jwt_symmetric.home;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${api-endpoint}")
public class HomeController {

    @GetMapping("")
    public String index() {
        return "Hello, Spring Security!";
    }

    @GetMapping("/private")
    public String connectToPrivatePath(Principal principal) {
        return "Hello, " + principal.getName() + "!";
    }

    @GetMapping("/common")
    public String common() {
        return "Hello, you have read scope!";
    }

}