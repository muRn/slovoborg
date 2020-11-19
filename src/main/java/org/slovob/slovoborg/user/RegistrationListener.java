package org.slovob.slovoborg.user;

import org.slovob.slovoborg.mail.Email;
import org.slovob.slovoborg.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private UserService service;

    @Autowired
    private MailService sendgrid;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Slovoborg Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/register/confirm/" + token;
        String message = "Please confirm your registration: " + confirmationUrl;
		Email confirmationEmail = new Email(recipientAddress, subject, message);

		sendgrid.sendEmail(confirmationEmail);
    }
}