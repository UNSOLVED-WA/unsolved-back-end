package com.unsolvedwa.unsolvedwa.domain.problem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SolvingProblemResponseDto extends ProblemResponseDto {
  private Long score;
  private String teamName;

  public SolvingProblemResponseDto(Long problemId, String problemTitle, Long tier, Long score, String teamName) {
    super(problemId, problemTitle, tier);
    this.score = score;
    this.teamName = teamName;
  }
}
