package com.unsolvedwa.unsolvedwa.domain.ranking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MonthRankingRequestDto {
  private Long teamId;

  public MonthRankingRequestDto(Long teamId){
    this.teamId = teamId;
  }
}
