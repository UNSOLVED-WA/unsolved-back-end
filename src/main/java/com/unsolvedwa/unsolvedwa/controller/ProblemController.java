package com.unsolvedwa.unsolvedwa.controller;

import com.unsolvedwa.unsolvedwa.domain.problem.Problem;
import com.unsolvedwa.unsolvedwa.domain.problem.ProblemService;
import com.unsolvedwa.unsolvedwa.domain.problem_team.ProblemTeamService;
import com.unsolvedwa.unsolvedwa.domain.problem_team.dto.ScoreDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    return ResponseEntity.ok(problemService.findByProblemId(id));
  }

  @Operation(description = "특정 문제 품")
  @PostMapping(value = "/solving")
  public ResponseEntity<List<ScoreDto>> postSolving(@Parameter Long user_id,
      @Parameter Long boj_id) {
    return ResponseEntity.ok(problemTeamService.solvingProblem(user_id, boj_id));
  }

  @Operation(description = "unsolved 리스트 조회")
  @GetMapping(value = "/{team_id}/{tier}")
  public ResponseEntity<List<Problem>> getUnsolvedProblems(@Parameter @PathVariable Long team_id,
      @Parameter @PathVariable Long tier) {
    //TODO: service 구현하여 작성
    return ResponseEntity.ok(new ArrayList<>());
  }
}
