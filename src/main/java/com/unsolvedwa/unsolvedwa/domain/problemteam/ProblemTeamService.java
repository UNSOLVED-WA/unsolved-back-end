package com.unsolvedwa.unsolvedwa.domain.problemteam;

import com.unsolvedwa.unsolvedwa.domain.problem.dto.ProblemResponseDto;
import com.unsolvedwa.unsolvedwa.domain.team.Team;
import com.unsolvedwa.unsolvedwa.domain.team.TeamRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;


@Service("problemTeamService")
@Transactional
@RequiredArgsConstructor
public class ProblemTeamService {

  private final ProblemTeamRepository problemTeamRepository;
  private final TeamRepository teamRepository;
  
  @Transactional(readOnly = true)
  public Optional<ProblemResponseDto> findUnsolvedRandomProblems(String teamName, Long tier) {
	  return problemTeamRepository.findUnsolvedRandomProblems(teamName, tier);
  }

    @Transactional(readOnly = true)
    public Optional<ProblemResponseDto> findRandomUnsolvedProblem(String teamName) throws NotFoundException {

      Optional<Team> optionalTeam = teamRepository.findByName(teamName);
      if (optionalTeam.isEmpty()) {
        throw new NotFoundException("teamName NotFound");
      }
        return problemTeamRepository.findUnsolvedRandomProblem(teamName);
    }
}
