package org.slovob.slovoborg.mail.mailgun;

import org.slovob.slovoborg.mail.Email;
import org.slovob.slovoborg.mail.MailService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

//@Component("mailgun")
@ConfigurationProperties(prefix = "mail.service.mailgun")
public class MailgunService implements MailService {
    private String domainName;
    private String apiKey;

    private RestTemplate restTemplate;

    public MailgunService() {
        this.restTemplate = new RestTemplate();
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    private HttpHeaders createHeaders(String username, String password){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(username, password);
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        return httpHeaders;
    }

    public static MultiValueMap<String, Object> emailToMultiValueMap(Email email) {
        MultiValueMap<String, Object> result = new LinkedMultiValueMap<>();
        result.add("to", email.getTo());
        result.add("subject", email.getSubject());
        result.add("text", email.getText());
        return result;
    }

    public void sendEmail(Email email) {
        MultiValueMap<String, Object> form = emailToMultiValueMap(email);
        form.add("from", "Excited User <mailgun@" + domainName + ">");

        ResponseEntity<MailgunResponse> responseEntity =
                restTemplate.exchange("https://api.mailgun.net/v3/{domainName}/messages", HttpMethod.POST,
                        new HttpEntity<>(form, createHeaders("api", apiKey)), MailgunResponse.class, domainName);

        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode != HttpStatus.OK) {
            throw new RuntimeException("Email not sent, mailgun returned " + statusCode);
        }

        if (responseEntity.getBody() != null) {
            String message = responseEntity.getBody().getMessage();
            if (!"Queued. Thank you.".equals(message)) {
                throw new RuntimeException("Got unexpected message from mailgun: " + message);
            }
        }
    }
}
