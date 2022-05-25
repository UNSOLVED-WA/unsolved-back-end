package com.unsolvedwa.unsolvedwa.domain.problemteam;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemTeamRepository extends JpaRepository<ProblemTeam, Long>,
    ProblemTeamRepositoryCustom {

}
