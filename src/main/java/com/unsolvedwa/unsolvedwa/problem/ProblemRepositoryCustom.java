package com.unsolvedwa.unsolvedwa.problem;

import java.util.List;

public interface ProblemRepositoryCustom {
    List<Problem> findUnsolvedByTeamId(Long teamId);
}
