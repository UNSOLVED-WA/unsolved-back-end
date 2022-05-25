package com.unsolvedwa.unsolvedwa.domain.problem_team;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemTeamRepository extends JpaRepository<ProblemTeam, Long>, ProblemTeamRepositoryCustom {
}
