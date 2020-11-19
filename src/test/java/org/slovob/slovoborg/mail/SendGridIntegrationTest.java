package org.slovob.slovoborg.mail;

import org.junit.jupiter.api.Test;
import org.slovob.slovoborg.mail.sendgrid.SendGridService;

public class SendGridIntegrationTest {
    @Test
    public void test() {
        MailService mailService = new SendGridService();
        Email email = new Email("xmurnx@gmail.com", "SendGrid API test", "If you read this everything is working as expected");
        mailService.sendEmail(email);
    }
}
