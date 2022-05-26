package com.unsolvedwa.unsolvedwa.domain.problem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long>, ProblemRepositoryCustom {
  public Problem findByProblemNumber(Long problemNumber);
}
