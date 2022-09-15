package com.unsolvedwa.unsolvedwa.domain.problemteam;
import java.util.List;
import com.unsolvedwa.unsolvedwa.domain.problem.Problem;

public interface ProblemTeamRepositoryCustom {

  ProblemTeam[] solvingProblem(Long user_id, Long boj_id);
  
  List<Problem> findUnsolvedProblem(Long tier, Long team_id);
}
