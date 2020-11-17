package org.slovob.slovoborg.mail.mailgun;

import org.slovob.slovoborg.mail.Email;
import org.slovob.slovoborg.mail.MailService;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component("mailgun")
public class MailgunService implements MailService {
    private static final String DOMAIN_NAME = "sandbox2486a9dd4f464c63a7d52bedd3a26b1a.mailgun.org";
    private static final String API_KEY = "b800dae6462a9d6dfe52027230ac7c40-ba042922-321bfb38";

    private RestTemplate restTemplate;

    public MailgunService() {
        this.restTemplate = new RestTemplate();
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
        form.add("from", "Excited User <mailgun@" + DOMAIN_NAME + ">");

        ResponseEntity<MailgunResponse> responseEntity =
                restTemplate.exchange("https://api.mailgun.net/v3/{domainName}/messages", HttpMethod.POST,
                        new HttpEntity<>(form, createHeaders("api", API_KEY)), MailgunResponse.class, DOMAIN_NAME);

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
