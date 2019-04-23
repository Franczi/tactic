package com.nerv.tactic.service;

import com.nerv.tactic.domain.model.AppUser;
import com.nerv.tactic.domain.repository.AppUserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

public class DafaultAuthenticatorProvider implements AuthenticationProvider {

    private final AppUserRepository repository;

    public DafaultAuthenticatorProvider(AppUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (authentication.getName() == null || authentication.getCredentials() == null) {
            return null;
        }

        if (authentication.getName().isEmpty() || authentication.getCredentials().toString().isEmpty()) {
            return null;
        }

        final Optional<AppUser> appUser = this.repository.findByUserEmail(authentication.getName());

        if (appUser.isPresent()) {
            final AppUser user = appUser.get();
            final String providedUserEmail = authentication.getName();
            final Object providedUserPassword = authentication.getCredentials();

            if (providedUserEmail.equalsIgnoreCase(user.getUserEmail())
                    && providedUserPassword.equals(user.getUserPass())) {
                return new UsernamePasswordAuthenticationToken(
                        user.getUserEmail(),
                        user.getUserPass(),
                        Collections.singleton(new SimpleGrantedAuthority(user.getUserRole())));
            }
        }

        throw new UsernameNotFoundException("Invalid username or password.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
