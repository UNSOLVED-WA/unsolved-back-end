package com.unsolvedwa.unsolvedwa.controller;

import com.unsolvedwa.unsolvedwa.domain.problem.Problem;
import com.unsolvedwa.unsolvedwa.domain.problem.ProblemService;
import com.unsolvedwa.unsolvedwa.domain.problem.dto.ProblemResponseDto;
import com.unsolvedwa.unsolvedwa.domain.problem.dto.SolvingProblemRequestDto;
import com.unsolvedwa.unsolvedwa.domain.problem.dto.SolvingProblemResponseDto;
import com.unsolvedwa.unsolvedwa.domain.problemteam.ProblemTeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Tag(name = "ProblemController")
@RequestMapping("/problems")
@RequiredArgsConstructor
public class ProblemController {

  private final ProblemService problemService;
  private final ProblemTeamService problemTeamService;

  @Operation(description = "문제조회")
  @GetMapping(value = "/{id}")
  public ResponseEntity<Problem> getProblem(@Parameter @PathVariable Long id) {
    try
    {
      return ResponseEntity.ok(problemService.findByProblemId(id));
    }
    catch (NotFoundException e)
    {
      return ResponseEntity.notFound().build();
    }
  }

  @Operation(description = "특정 팀, 특정 티어의 unsolved problem 1개 랜덤으로 조회")
  @GetMapping(value = "/unsolved/random/{teamId}/{tier}")
  public ResponseEntity<ProblemResponseDto> getUnsolvedProblems(@Parameter @PathVariable Long teamId,
      @Parameter @PathVariable Long tier) {
	  Optional<ProblemResponseDto> result = problemTeamService.findUnsolvedRandomProblems(teamId, tier);
	  if (result.isEmpty())
		  return ResponseEntity.noContent().build();
	  return ResponseEntity.ok(result.get());
	 
    
  }

  @Operation(description = "특정 팀, 특정 티어의 unsolved problem 모두 리스트로 조회")
  @GetMapping(value = "/unsolved/{teamId}/{tier}")
  public ResponseEntity<List<ProblemResponseDto>> getUnsolvedProblemsByTeamAndTier(@Parameter @PathVariable Long teamId, @Parameter @PathVariable Long tier){
    try
    {
      return ResponseEntity.ok(problemService.findUnsolvedProblemsByTeamAndTier(teamId, tier));
    }
    catch (NotFoundException e)
    {
      return ResponseEntity.notFound().build();
    }
  }

  @Operation(description = "해결한 문제 서버에 전송 후 획득한 점수 응답")
  @PostMapping(value = "/solving")
  public ResponseEntity<List<SolvingProblemResponseDto>> solveProblem(@RequestBody
      SolvingProblemRequestDto solvingProblemRequestDto) {
    String bojId = solvingProblemRequestDto.getBojId();
    Long problemNumber = solvingProblemRequestDto.getProblemNumber();

    try
    {
      return ResponseEntity.ok(problemService.solveProblem(bojId, problemNumber));
    }
    catch (NotFoundException e)
    {
      return ResponseEntity.notFound().build();
    }
  }
}
