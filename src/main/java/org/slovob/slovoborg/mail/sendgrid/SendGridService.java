package org.slovob.slovoborg.mail.sendgrid;

import org.slovob.slovoborg.mail.Email;
import org.slovob.slovoborg.mail.MailService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("sendgrid")
@ConfigurationProperties(prefix = "mail.service.sendgrid")
public class SendGridService implements MailService {
    private String apiKey;
    private String from;

    private final RestTemplate restTemplate;

    public SendGridService() {
        this.restTemplate = new RestTemplate();
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(apiKey);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    private SendGridRequest emailToRequestObject(Email email) {
        SendGridRequest request = new SendGridRequest();
        Personalization personalization = new Personalization();
        personalization.addRecipient(new Address(email.getTo()));
        request.addPersonalization(personalization);
        request.setFrom(new Address(from));
        request.setSubject(email.getSubject());
        request.addContent(new Content("text/plain", email.getText()));
        return request;
    }

    @Override
    public void sendEmail(Email email) {
        SendGridRequest request = emailToRequestObject(email);
        HttpHeaders headers = createHeaders();

        ResponseEntity<String> responseEntity = restTemplate.exchange("https://api.sendgrid.com/v3/mail/send", HttpMethod.POST, new HttpEntity<>(request, headers), String.class);

        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode != HttpStatus.ACCEPTED) {
            throw new RuntimeException("SendGrid returned " + statusCode);
        }

        String responseBody = responseEntity.getBody();
        if (responseBody != null && !responseBody.isEmpty()) {
            throw new RuntimeException("SendGrid returned " + responseBody);
        }
    }
}
