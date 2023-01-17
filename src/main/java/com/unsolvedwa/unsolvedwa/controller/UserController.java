package com.unsolvedwa.unsolvedwa.controller;

import com.unsolvedwa.unsolvedwa.domain.problemteam.ProblemTeamService;
import com.unsolvedwa.unsolvedwa.domain.user.UserService;
import com.unsolvedwa.unsolvedwa.domain.user.dto.UserResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "UserController")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final ProblemTeamService problemTeamService;

  @Operation(description = "유저 백준 아이디로 유저 정보 조회")
  @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = UserResDTO.class)))
  @GetMapping(value = "/{bojId}")
  public ResponseEntity<UserResDTO> getUser(@Parameter @PathVariable String bojId) {
    try {
    	return ResponseEntity.ok(userService.findByBojId(bojId));
    }
    catch (NotFoundException e) {
    	return ResponseEntity.notFound().build();
    }
  }
}