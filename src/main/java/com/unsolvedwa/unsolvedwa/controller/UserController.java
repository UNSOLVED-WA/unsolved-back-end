package com.unsolvedwa.unsolvedwa.controller;

import com.unsolvedwa.unsolvedwa.domain.problemteam.ProblemTeamService;
import com.unsolvedwa.unsolvedwa.domain.user.User;
import com.unsolvedwa.unsolvedwa.domain.user.UserService;
import com.unsolvedwa.unsolvedwa.domain.user.dto.UserResDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

  @Operation(description = "유저조회")
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