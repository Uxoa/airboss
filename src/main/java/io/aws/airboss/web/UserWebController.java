package io.aws.airboss.web;

import io.aws.airboss.users.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class UserWebController {
    
    private final GlobalWebServices globalWebServices;
    
    public UserWebController(GlobalWebServices globalWebServices) {
        this.globalWebServices = globalWebServices;
    }
    
  
}
