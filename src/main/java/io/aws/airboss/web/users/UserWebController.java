package io.aws.airboss.web.users;

import io.aws.airboss.web.GlobalWebServices;
import org.springframework.stereotype.Controller;

@Controller
public class UserWebController {
    
    private final GlobalWebServices globalWebServices;
    
    public UserWebController(GlobalWebServices globalWebServices) {
        this.globalWebServices = globalWebServices;
    }
    
   
}
