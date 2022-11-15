package com.unsolvedwa.unsolvedwa.domain.ranking.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MonthRankingResponseDto {
  private String teamName;
  private String bojId;
  private Long score;
  private LocalDateTime createAt;

  @QueryProjection
  public MonthRankingResponseDto(String teamName, String bojId, Long score, LocalDateTime createAt) {
    this.teamName = teamName;
    this.bojId = bojId;
    this.score = score;
    this.createAt = createAt;
  }
}
