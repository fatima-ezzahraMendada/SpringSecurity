package ma.enset.patientservelt1.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
    @GetMapping("/AccessDenied")
    public String accessDenied(){
        return "AccessDenied";
    }
}
