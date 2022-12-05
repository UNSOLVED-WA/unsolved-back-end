package com.unsolvedwa.unsolvedwa.domain.ranking.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MonthRankingHistoryResponseDto {
  private String teamName;
  private String bojId;
  private Long score;
  private Long ranking;
  private LocalDateTime createAt;

  @QueryProjection
  public MonthRankingHistoryResponseDto(String teamName, String bojId, Long score, Long ranking, LocalDateTime createAt) {
    this.teamName = teamName;
    this.bojId = bojId;
    this.score = score;
    this.ranking = ranking;
    this.createAt = createAt;
  }
}
