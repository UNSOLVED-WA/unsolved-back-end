package com.unsolvedwa.unsolvedwa.domain.problemteam;

import java.util.Optional;


import com.unsolvedwa.unsolvedwa.domain.problem.dto.ProblemResponseDto;

public interface ProblemTeamRepositoryCustom {
 
  public Optional<ProblemResponseDto> findUnsolvedRandomProblems(Long teamId, Long tier);

}
