package com.unsolvedwa.unsolvedwa.user;

import com.unsolvedwa.unsolvedwa.user.dto.Score;

public interface UserCustomRepository {
    Score[] solvingProblem(Long user_id, Long boj_id);
}
