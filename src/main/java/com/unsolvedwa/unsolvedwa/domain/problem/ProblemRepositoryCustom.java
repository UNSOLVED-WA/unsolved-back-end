package com.unsolvedwa.unsolvedwa.domain.problem;

import com.unsolvedwa.unsolvedwa.domain.problem.dto.ProblemResponseDto;
import java.util.List;

public interface ProblemRepositoryCustom {
  public List<ProblemResponseDto> findUnsolvedProblemsByTeamAndTier(Long teamId, Long tier);
}
