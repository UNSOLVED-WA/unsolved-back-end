package com.unsolvedwa.unsolvedwa.controller;

import com.unsolvedwa.unsolvedwa.domain.problemteam.ProblemTeamService;
import com.unsolvedwa.unsolvedwa.domain.problemteam.dto.ScoreDto;
import com.unsolvedwa.unsolvedwa.domain.user.User;
import com.unsolvedwa.unsolvedwa.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "UserController")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final ProblemTeamService problemTeamService;

  @Operation(description = "유저조회")
  @GetMapping(value = "/{id}")
  public ResponseEntity<User> getUser(@Parameter @PathVariable Long id) {
    return ResponseEntity.ok(userService.findById(id));
  }

  @Operation(description = "특정 문제 품")
  @PostMapping(value = "/solving")
  public ResponseEntity<List<ScoreDto>> postSolving(@Parameter Long user_id,
      @Parameter Long boj_id) {
    return ResponseEntity.ok(problemTeamService.solvingProblem(user_id, boj_id));
  }
}
