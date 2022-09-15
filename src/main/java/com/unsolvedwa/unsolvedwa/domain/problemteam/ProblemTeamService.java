package com.unsolvedwa.unsolvedwa.domain.problemteam;

import com.unsolvedwa.unsolvedwa.domain.problemteam.dto.ScoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.unsolvedwa.unsolvedwa.domain.problem.dto.UnsolvedDto;
import com.unsolvedwa.unsolvedwa.domain.problem.Problem;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

@Service("problemTeamService")
@Transactional
@RequiredArgsConstructor
public class ProblemTeamService {

  private final ProblemTeamRepository problemTeamRepository;

  public List<ScoreDto> solvingProblem(Long user_id, Long boj_id) {
    return ScoreDto.ofArray(problemTeamRepository.solvingProblem(user_id, boj_id));
  }
  
  public List<UnsolvedDto> findUnsolvedProblem(Long tier, Long team_id) throws NotFoundException {
	  // [Temp] 임시
	  List<Problem> optList = problemTeamRepository.findUnsolvedProblem(tier, team_id);
	  if (optList.isEmpty())
		  throw new NotFoundException();
	  List<UnsolvedDto> list = new ArrayList<>();
	  for (Problem prob : optList) {
		  UnsolvedDto dto = new UnsolvedDto();
		  dto.setId(prob.getId());
		  dto.setProblemNumber(prob.getProblemNumber());
		  dto.setTier(prob.getTier());
		  dto.setTitle(prob.getTitle());
		  list.add(dto);
	  }
	  System.out.println("service");
	  System.out.println(list);
	  return list;
  }
}
