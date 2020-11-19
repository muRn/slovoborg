package org.slovob.slovoborg.mail.sendgrid;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SendGridRequest {
    private List<Personalization> personalizations = new ArrayList<>();
    private Address from;
    private String subject;
    private List<Content> content = new ArrayList<>();

    public void addPersonalization(Personalization personalization) {
        this.personalizations.add(personalization);
    }

    public void addContent(Content content) {
        this.content.add(content);
    }
}

@Data
class Personalization {
    private List<Address> to = new ArrayList<>();

    public void addRecipient(Address address) {
        this.to.add(address);
    }
}

@Data
class Address {
    private String email;

    public Address(String email) {
        this.email = email;
    }
}

@Data
class Content {
    private String type;
    private String value;

    public Content(String type, String value) {
        this.type = type;
        this.value = value;
    }
}
