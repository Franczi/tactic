package com.nerv.tactic.domain.repository;

import com.nerv.tactic.domain.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<Step, Long> {
}
