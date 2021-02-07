package org.slovob.slovoborg.user;

import lombok.extern.slf4j.Slf4j;
import org.slovob.slovoborg.mail.Email;
import org.slovob.slovoborg.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.UUID;

@Slf4j
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private MailService sendgrid;
    private UserService service;
    private MessageSource messageSource;

    public RegistrationListener(MailService sendgrid, UserService service, MessageSource messageSource) {
        this.sendgrid = sendgrid;
        this.service = service;
        this.messageSource = messageSource;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        Locale locale = event.getLocale();
        String subject = messageSource.getMessage("regConfirm.subject", null, locale);
        log.info("Subject: {}", subject);
        String confirmationUrl = event.getAppUrl() + "/register/confirm/" + token;
        String message = messageSource.getMessage("regConfirm.text", null, locale) + ": " + confirmationUrl;
        log.info("Message: {}", message);
		Email confirmationEmail = new Email(recipientAddress, subject, message);

//		sendgrid.sendEmail(confirmationEmail);
    }
}