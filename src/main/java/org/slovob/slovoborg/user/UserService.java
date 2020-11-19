package org.slovob.slovoborg.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByName(username);
        if (userOpt.isPresent()) {
            return userOpt.get();
        }

        throw new UsernameNotFoundException("User '" + username + "' not found");
    }

    public User registerNewUserAccount(RegistrationForm rf) {
        User user = rf.toUser(passwordEncoder);
        return userRepository.save(user);
    }

    public void createVerificationToken(User user, String token) {
        VerificationToken vt = new VerificationToken();
        vt.setUser(user);
        vt.setToken(token);
        tokenRepository.save(vt);
    }

    public VerificationToken getVerificationToken(String token) {
        Optional<VerificationToken> tokenOpt = tokenRepository.findByToken(token);
        return tokenOpt.orElse(null);
    }

    public void activateUser(User user) {
        user.setActive(true);
        userRepository.save(user);
    }
}
