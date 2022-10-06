package com.unsolvedwa.unsolvedwa.domain.problemteam;

import com.unsolvedwa.unsolvedwa.domain.problem.Problem;
import com.unsolvedwa.unsolvedwa.domain.team.Team;
import com.unsolvedwa.unsolvedwa.domain.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemTeamRepository extends JpaRepository<ProblemTeam, Long>,
    ProblemTeamRepositoryCustom {
  public Optional<ProblemTeam> findByProblemAndTeam(Problem problem, Team team);
  public List<ProblemTeam> findAllByProblem(Problem problem);
  public List<ProblemTeam> findAllByTeam(Team team);
  public List<ProblemTeam> findAllByUserAndProblem(User user, Problem problem);
  public List<ProblemTeam> findAllByTeamAndProblem(Team team, Problem problem);
}