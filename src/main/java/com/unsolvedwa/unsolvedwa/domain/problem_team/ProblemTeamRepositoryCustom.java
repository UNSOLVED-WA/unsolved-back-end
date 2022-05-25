package com.unsolvedwa.unsolvedwa.domain.problem_team;

public interface ProblemTeamRepositoryCustom {
    ProblemTeam[] solvingProblem(Long user_id, Long boj_id);
}
