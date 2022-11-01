package com.unsolvedwa.unsolvedwa.controller;

import com.unsolvedwa.unsolvedwa.domain.team.Team;
import com.unsolvedwa.unsolvedwa.domain.team.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "TeamController")
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

  private final TeamService teamService;

  @Operation(description = "팀조회")
  @GetMapping(value = "/{id}")
  public ResponseEntity<Team> getTeam(@Parameter @PathVariable Long id) {
    return ResponseEntity.ok(teamService.findByTeamId(id));
  }
}
