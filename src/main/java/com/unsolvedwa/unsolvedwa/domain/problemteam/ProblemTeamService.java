package com.unsolvedwa.unsolvedwa.domain.problemteam;

import com.unsolvedwa.unsolvedwa.domain.problemteam.dto.ScoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("problemTeamService")
@Transactional
@RequiredArgsConstructor
public class ProblemTeamService {

  private final ProblemTeamRepository problemTeamRepository;

  public List<ScoreDto> solvingProblem(Long user_id, Long boj_id) {
    return ScoreDto.ofArray(problemTeamRepository.solvingProblem(user_id, boj_id));
  }
}
