package com.unsolvedwa.unsolvedwa.domain.problemteam;

public interface ProblemTeamRepositoryCustom {

  ProblemTeam[] solvingProblem(Long user_id, Long boj_id);
}
