package com.unsolvedwa.unsolvedwa.controller;

import com.unsolvedwa.unsolvedwa.domain.problem.Problem;
import com.unsolvedwa.unsolvedwa.domain.problem.ProblemService;
import com.unsolvedwa.unsolvedwa.domain.problem.dto.ProblemResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "ProblemController")
@RequestMapping("/problems")
@RequiredArgsConstructor
public class ProblemController {

  private final ProblemService problemService;

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

  @Operation(description = "unsolved 리스트 조회")
  @GetMapping(value = "/{teamId}/{tier}")
  public ResponseEntity<List<Problem>> getUnsolvedProblems(@Parameter @PathVariable Long teamId,
      @Parameter @PathVariable Long tier) {
    //TODO: service 구현하여 작성
    return ResponseEntity.ok(new ArrayList<>());
  }

  @Operation(description = "특정 팀, 티어의 unsolved problem 리스트 조회")
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
}
