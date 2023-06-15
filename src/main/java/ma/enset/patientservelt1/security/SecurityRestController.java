package ma.enset.patientservelt1.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityRestController {
    //oject (object spring security) authentification permet de savoir session id, username, role du utilisateur identifier
    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication){
        //Authentication authentication1= SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }
}
