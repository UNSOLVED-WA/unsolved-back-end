package com.unsolvedwa.unsolvedwa.domain.problem.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemResponseDto {
  private Long problemId;
  private Long problemNumber;
  private Long tier;
  private String problemTitle;

  @QueryProjection
  public ProblemResponseDto(Long problemId, Long problemNumber, String problemTitle, Long tier) {
    this.problemId = problemId;
    this.problemNumber = problemNumber;
    this.problemTitle = problemTitle;
    this.tier = tier;
  }
}