package org.slovob.slovoborg.security;

import org.slovob.slovoborg.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin")
                    .hasRole("ADMIN")
                .antMatchers("/opinion", "/definition")
                    .hasAnyRole("ADMIN", "USER")
                .antMatchers("/", "/**")
                    .permitAll()
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .successHandler(getAuthenticationSuccessHandler())
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .exceptionHandling()
                        .defaultAuthenticationEntryPointFor(getAjaxAuthenticationEntryPoint(), new AntPathRequestMatcher("/opinion"));
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    public AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    private AuthenticationEntryPoint getAjaxAuthenticationEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(encoder());
    }
}