package com.unsolvedwa.unsolvedwa.domain.team.dto;

import com.unsolvedwa.unsolvedwa.domain.team.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamResponseDto {
  private Long teamId;
  private String teamName;

  public TeamResponseDto(Long teamId, String teamName) {
    this.teamId = teamId;
    this.teamName = teamName;
  }

  public TeamResponseDto(Team team) {
    this.teamId = team.getId();
    this.teamName = team.getName();
  }
}
