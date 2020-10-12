package org.slovob.slovoborg.user;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {
    private String username;
    private String email;
    private String password;

    public User toUser(PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        return user;
    }
}
