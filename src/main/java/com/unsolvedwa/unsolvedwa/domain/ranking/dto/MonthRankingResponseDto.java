package com.unsolvedwa.unsolvedwa.domain.ranking.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MonthRankingResponseDto {
  private String teamName;
  private String bojId;
  private Long score;

  @QueryProjection
  public MonthRankingResponseDto(String teamName, String bojId, Long score) {
    this.teamName = teamName;
    this.bojId = bojId;
    this.score = score;
  }
}
