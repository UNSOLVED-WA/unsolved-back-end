package com.unsolvedwa.unsolvedwa.domain.problemteam;

import com.unsolvedwa.unsolvedwa.domain.problem.dto.ProblemResponseDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("problemTeamService")
@Transactional
@RequiredArgsConstructor
public class ProblemTeamService {

  private final ProblemTeamRepository problemTeamRepository;
  
  @Transactional(readOnly = true)
  public Optional<ProblemResponseDto> findUnsolvedRandomProblems(String teamName, Long tier) {
	  
	  return problemTeamRepository.findUnsolvedRandomProblems(teamName, tier);
  }

}
