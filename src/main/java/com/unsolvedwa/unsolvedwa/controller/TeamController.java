package com.unsolvedwa.unsolvedwa.controller;

import com.unsolvedwa.unsolvedwa.domain.team.Team;
import com.unsolvedwa.unsolvedwa.domain.team.TeamService;
import com.unsolvedwa.unsolvedwa.domain.team.dto.TeamResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

@RestController
@Tag(name = "TeamController")
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

  private final TeamService teamService;

  @Operation(description = "팀 아이디로 팀 정보 조회")
  @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = TeamResponseDto.class)))
  @GetMapping(value = "/{id}")
  public ResponseEntity<TeamResponseDto> getTeam(@Parameter @PathVariable Long id) {
    Team team = teamService.findByTeamId(id);
    return ResponseEntity.ok(new TeamResponseDto(team));
  }

  @Operation(description = "팀 이름으로 팀 정보 조회")
  @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = TeamResponseDto.class)))
  @GetMapping(value = "/name/{teamName}")
  public ResponseEntity<TeamResponseDto> getTeam(@Parameter @PathVariable String teamName) {
    try {
      Team team = teamService.findByTeamName(teamName);
      return ResponseEntity.ok(new TeamResponseDto(team));
    }
    catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
