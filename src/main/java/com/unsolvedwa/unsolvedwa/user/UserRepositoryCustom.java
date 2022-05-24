package com.unsolvedwa.unsolvedwa.user;

import com.unsolvedwa.unsolvedwa.team.ProblemTeam;

public interface UserRepositoryCustom {
    ProblemTeam[] solvingProblem(Long user_id, Long boj_id);
}
