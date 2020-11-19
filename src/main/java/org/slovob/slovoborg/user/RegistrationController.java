package org.slovob.slovoborg.user;

import org.slovob.slovoborg.mail.Email;
import org.slovob.slovoborg.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserRepository repo;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailService sendgrid;

    public RegistrationController(UserRepository repo, PasswordEncoder passwordEncoder, MailService sendgrid) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.sendgrid = sendgrid;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }

    @PostMapping
    public String register(@Valid RegistrationForm rf, Errors errors, Model model) {
        String email = rf.getEmail();
        Optional<User> userOpt = repo.findByEmailAndActive(email, true);
        userOpt.ifPresent(user -> errors.rejectValue("email", "already in use",
                "Email is already in use"));

        if (errors.hasErrors()) {
            return "registration";
        }

        User user = rf.toUser(passwordEncoder);
        repo.save(user);

        Email confirmationEmail = new Email(email, "Slovoborg confirmation", "Please confirm");
        sendgrid.sendEmail(confirmationEmail);

        model.addAttribute("email", email);
        return "postreg";
    }
}
