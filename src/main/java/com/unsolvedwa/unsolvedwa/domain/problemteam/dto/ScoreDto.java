package com.unsolvedwa.unsolvedwa.domain.problemteam.dto;

import com.unsolvedwa.unsolvedwa.domain.problemteam.ProblemTeam;

import java.util.ArrayList;
import java.util.List;

public class ScoreDto {

  private Long teamId;
  private Long score;

  private ScoreDto(Long score, Long team_id) {
    this.score = score;
    this.teamId = team_id;
  }

  public static ScoreDto of(ProblemTeam user_team) {
    return new ScoreDto(user_team.getScore(), user_team.getTeam().getId());
  }

  public static List<ScoreDto> ofArray(ProblemTeam[] user_team) {
    List<ScoreDto> score = new ArrayList<ScoreDto>();
    for (int i = 0; i < user_team.length; i++) {
      score.add(of(user_team[i]));
    }
    return score;
  }
}

