package com.nerv.tactic.domain.repository;

import com.nerv.tactic.domain.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUserEmail(String UserEmail);
}
