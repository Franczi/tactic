package com.nerv.tactic.service;

import com.nerv.tactic.domain.model.AppUser;
import com.nerv.tactic.domain.repository.AppUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserDetailsService implements UserDetailsService {
    private final AppUserRepository repository;

    public DefaultUserDetailsService(AppUserRepository repository) {
        this.repository = repository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final Optional<AppUser> userEntity = repository.findByUserEmail(username);

        if (userEntity.isPresent()) {
            final AppUser appUser = userEntity.get();

            return new User(appUser.getUserEmail(),
                    passwordNoEncoding(appUser),
                    Collections.singletonList(new SimpleGrantedAuthority(appUser.getUserRole())));
        }

        return null;
    }


    private String passwordNoEncoding(AppUser appUser) {
        // you can use one of bcrypt/noop/pbkdf2/scrypt/sha256
        // more: https://spring.io/blog/2017/11/01/spring-security-5-0-0-rc1-released#password-encoding
        return "{noop}" + appUser.getUserPass();
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Collections.emptyList();
    }
}
