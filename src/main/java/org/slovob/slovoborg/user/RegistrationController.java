package org.slovob.slovoborg.user;

import org.slovob.slovoborg.exception.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserRepository repo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String register(RegistrationForm rf) {
        String email = rf.getEmail();
        if (repo.findByEmail(email).isPresent()) {
            throw new EmailExistsException("Email '" + email + "' is already in use");
        }

        User user = rf.toUser(passwordEncoder);
        repo.save(user);
        return "redirect:/login";
    }
}
