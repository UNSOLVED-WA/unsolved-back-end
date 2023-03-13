package com.unsolvedwa.unsolvedwa.domain.problemteam;

import com.unsolvedwa.unsolvedwa.domain.problem.dto.ProblemResponseDto;
import java.util.List;
import java.util.Optional;

public interface ProblemTeamRepositoryCustom {
 
  public Optional<ProblemResponseDto> findUnsolvedRandomProblems(String teamName, Long tier);
  public Optional<ProblemResponseDto> findUnsolvedRandomProblem(String teamName);
  public List<Long> findAllProblemNumberByTeamOrderById(Long teamId);
  public List<Long> findAllIdByTeamAndUser(Long teamId, Long userId);
}
