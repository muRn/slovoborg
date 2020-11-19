package org.slovob.slovoborg.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController {
    @Autowired
    ApplicationEventPublisher eventPublisher;
    private UserRepository repo;
    private UserService userService;

    public RegistrationController(ApplicationEventPublisher eventPublisher, UserRepository repo, UserService userService) {
        this.eventPublisher = eventPublisher;
        this.repo = repo;
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }

    @PostMapping
    public String register(@Valid RegistrationForm rf, Errors errors, Model model, HttpServletRequest request) {
        String username = rf.getUsername();
        Optional<User> userOpt = repo.findByName(username);
        userOpt.ifPresent(user -> errors.rejectValue("name", "already in use", "User name is already in use"));

        String email = rf.getEmail();
        userOpt = repo.findByEmailAndActive(email, true);
        userOpt.ifPresent(user -> errors.rejectValue("email", "already in use", "Email is already in use"));

        if (errors.hasErrors()) {
            return "registration";
        }

        User user = userService.registerNewUserAccount(rf);

        String appUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        log.info("app url: " + appUrl);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, appUrl));

        model.addAttribute("email", email);
        return "postreg";
    }

    @GetMapping("/confirm/{token}")
    public String confirmEmail(@PathVariable("token") String token) {
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            // someone is too smart, I won't even bother showing error message in this case
            log.info("Token " + token + " not found in VERIFICATION_TOKEN table");
            return "redirect:/";
        }

        User user = verificationToken.getUser();
        userService.activateUser(user);
        log.info("User " + user.getName() + " activated");

        return "redirect:/login";
    }
}
