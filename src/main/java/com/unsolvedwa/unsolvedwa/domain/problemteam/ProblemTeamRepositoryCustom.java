package com.unsolvedwa.unsolvedwa.domain.problemteam;
import java.util.List;
import com.unsolvedwa.unsolvedwa.domain.problem.Problem;
import com.unsolvedwa.unsolvedwa.domain.problem.dto.ProblemResponseDto;

public interface ProblemTeamRepositoryCustom {

  ProblemTeam[] solvingProblem(Long user_id, Long boj_id);
  
  public ProblemResponseDto findUnsolvedRandomProblems(Long teamId, Long tier);
}
