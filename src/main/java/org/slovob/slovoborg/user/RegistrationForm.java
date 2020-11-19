package org.slovob.slovoborg.user;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegistrationForm {
    @Size(min=3, message="Name must be at least 3 characters long")
    @Pattern(regexp="^(?!adm)\\w+$", message="Name should not resemble administration accounts")
    private String username;

    @Email(message = "Invalid email")
    private String email;

    private String password;

    public User toUser(PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setName(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        user.setActive(false); // explicit > implicit
        return user;
    }
}
