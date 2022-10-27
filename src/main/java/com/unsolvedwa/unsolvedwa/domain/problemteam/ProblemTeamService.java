package com.unsolvedwa.unsolvedwa.domain.problemteam;

import com.unsolvedwa.unsolvedwa.domain.problemteam.dto.ScoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.unsolvedwa.unsolvedwa.domain.problem.Problem;
import com.unsolvedwa.unsolvedwa.domain.problem.dto.ProblemResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;


@Service("problemTeamService")
@Transactional
@RequiredArgsConstructor
public class ProblemTeamService {

  private final ProblemTeamRepository problemTeamRepository;


  public List<ScoreDto> solvingProblem(Long user_id, Long boj_id) {
    return ScoreDto.ofArray(problemTeamRepository.solvingProblem(user_id, boj_id));
  }
  
  
  @Transactional(readOnly = true)
  public Optional<ProblemResponseDto> findUnsolvedRandomProblems(Long teamId, Long tier) {
	  
	  return problemTeamRepository.findUnsolvedRandomProblems(teamId, tier);
  }

}
