package com.unsolvedwa.unsolvedwa.domain.team;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long>, TeamRepositoryCustom {
  public Optional<Team> findByName(String name);
}
