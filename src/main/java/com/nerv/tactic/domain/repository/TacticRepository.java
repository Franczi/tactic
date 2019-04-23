package com.nerv.tactic.domain.repository;

import com.nerv.tactic.domain.model.Tactic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TacticRepository extends JpaRepository<Tactic, Long> {
}
