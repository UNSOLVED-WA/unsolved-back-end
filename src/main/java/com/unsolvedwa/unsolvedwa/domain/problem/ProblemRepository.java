package com.unsolvedwa.unsolvedwa.domain.problem;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long>, ProblemRepositoryCustom {
  public Optional<Problem> findByProblemNumber(Long problemNumber);
}
