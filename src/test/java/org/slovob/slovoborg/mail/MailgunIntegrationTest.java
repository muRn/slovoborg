package org.slovob.slovoborg.mail;

import org.junit.jupiter.api.Test;
import org.slovob.slovoborg.mail.mailgun.MailgunService;
import org.springframework.web.client.RestTemplate;

public class MailgunIntegrationTest {
    @Test
    public void test() {
        MailService mailService = new MailgunService(new RestTemplate());
        Email email = new Email("dutytmp+yfytl@gmail.com", "Mailgun API test", "If you read this everything is working as expected");
        mailService.sendEmail(email);
    }
}
