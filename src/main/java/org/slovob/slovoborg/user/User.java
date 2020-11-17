package org.slovob.slovoborg.user;

import lombok.Data;
import org.slovob.slovoborg.definition.Definition;
import org.slovob.slovoborg.opinion.Opinion;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String email;
    private boolean emailConfirmed;
    private String password;
    private String role;

    @OneToMany(mappedBy = "author")
    private List<Definition> definitions;

    @OneToMany(mappedBy = "user")
    private List<Opinion> opinions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
