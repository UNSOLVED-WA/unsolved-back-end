package com.unsolvedwa.unsolvedwa.domain.problem;

import com.unsolvedwa.unsolvedwa.domain.problem.dto.ProblemResponseDto;
import com.unsolvedwa.unsolvedwa.domain.problem.dto.SolvingProblemResponseDto;
import com.unsolvedwa.unsolvedwa.domain.problemteam.ProblemTeam;
import com.unsolvedwa.unsolvedwa.domain.problemteam.ProblemTeamRepository;
import com.unsolvedwa.unsolvedwa.domain.ranking.Ranking;
import com.unsolvedwa.unsolvedwa.domain.ranking.RankingRepository;
import com.unsolvedwa.unsolvedwa.domain.team.Team;
import com.unsolvedwa.unsolvedwa.domain.team.TeamRepository;
import com.unsolvedwa.unsolvedwa.domain.user.User;
import com.unsolvedwa.unsolvedwa.domain.user.UserRepository;
import com.unsolvedwa.unsolvedwa.domain.userteam.UserTeam;
import com.unsolvedwa.unsolvedwa.domain.userteam.UserTeamRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ProblemService")
@Transactional
@RequiredArgsConstructor
public class ProblemService {

  private final ProblemRepository problemRepository;
  private final ProblemTeamRepository problemTeamRepository;
  private final TeamRepository teamRepository;
  private final UserRepository userRepository;
  private final UserTeamRepository userTeamRepository;
  private final RankingRepository rankingRepository;

  private LocalDateTime getCurMonthDateTime(){
    LocalDateTime curDateTime = LocalDateTime.now();
    return LocalDateTime.of(curDateTime.getYear(), curDateTime.getMonth(), 1, 0, 0);
  }

  @Transactional(readOnly = true)
  public Problem findByProblemId(Long id) throws NotFoundException {
    Optional<Problem> optionalProblem = problemRepository.findById(id);
    if (optionalProblem.isEmpty()) {
      throw new NotFoundException();
    }
    return optionalProblem.get();
  }

  @Transactional(readOnly = true)
  public List<ProblemResponseDto> findUnsolvedProblemsByTeamAndTier(Long teamId, Long tier) throws NotFoundException {
    if (tier < 0L || tier > 33L)
    {
      throw new NotFoundException();
    }
    Optional<Team> temp = teamRepository.findById(teamId);
    if (temp.isEmpty())
    {
      throw new NotFoundException();
    }
    return problemRepository.findUnsolvedProblemsByTeamAndTier(teamId, tier);
  }

  @Transactional(readOnly = false)
  public List<SolvingProblemResponseDto> solveProblem(Long userId, Long problemNumber) throws NotFoundException {
    Optional<User> user = userRepository.findById(userId);

    if (user.isEmpty()) {
      throw new NotFoundException();
    }

    Optional<Problem> problem = problemRepository.findByProblemNumber(problemNumber);

    if (problem.isEmpty()) {
      throw new NotFoundException();
    }

    List<UserTeam> userTeams = userTeamRepository.findAllByUser(user.get());
    List<ProblemTeam> problemTeams = problemTeamRepository.findAllByUserAndProblem(user.get(), problem.get());
    List<SolvingProblemResponseDto> solvingProblemResponseDtoList = new ArrayList<>();

    if (userTeams.size() == problemTeams.size()) {
      return solvingProblemResponseDtoList;
    }

    LocalDateTime startTime = getCurMonthDateTime();

    for (int i = 0; i < userTeams.size(); i++) {
      UserTeam userTeam = userTeams.get(i);
      Team curTeam = userTeam.getTeam();

      Optional<ProblemTeam> problemTeam = problemTeamRepository.findByProblemAndTeam(problem.get(), curTeam);
      if (problemTeam.isEmpty()) {
        ProblemTeam solvedProblemTeam = new ProblemTeam(problem.get(), curTeam, user.get());
        problemTeamRepository.save(solvedProblemTeam);

        userTeam.increaseScore(1L);
        userTeamRepository.save(userTeam);

        Optional<Ranking> optionalRanking = rankingRepository.findByTeamAndUserAndCreateAtAfter(curTeam, user.get(), startTime);
        Ranking ranking;
        if (optionalRanking.isEmpty()) {
          ranking = new Ranking(user.get(), curTeam);
        }
        else {
          ranking = optionalRanking.get();
        }
        ranking.increaseScore(1L);
        rankingRepository.save(ranking);

        SolvingProblemResponseDto solvingProblemResponseDto = new SolvingProblemResponseDto(problem.get().getId(), problem.get().getTitle(), problem.get().getTier(), 1L, curTeam.getName());
        solvingProblemResponseDtoList.add(solvingProblemResponseDto);
      }
    }
    return solvingProblemResponseDtoList;
  }
}
