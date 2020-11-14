package org.slovob.slovoborg.user;

import org.slovob.slovoborg.exception.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }

    @PostMapping
    public String register(@Valid RegistrationForm rf, Errors errors) {
        if (errors.hasErrors()) {
            return "registration";
        }

        String email = rf.getEmail();
        if (repo.findByEmail(email).isPresent()) {
            throw new EmailExistsException("Email '" + email + "' is already in use");
        }

        User user = rf.toUser(passwordEncoder);
        repo.save(user);
        return "redirect:/login";
    }
}
