package org.slovob.slovoborg.mail;

import org.junit.jupiter.api.Test;
import org.slovob.slovoborg.mail.mailgun.MailgunService;

public class MailgunIntegrationTest {
    @Test
    public void test() {
        MailService mailService = new MailgunService();
        Email email = new Email("dutytmp+yfytl@gmail.com", "Mailgun API test", "If you read this everything is working as expected");
        mailService.sendEmail(email);
    }
}
