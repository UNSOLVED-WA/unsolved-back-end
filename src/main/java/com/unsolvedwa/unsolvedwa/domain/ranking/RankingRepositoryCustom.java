package com.unsolvedwa.unsolvedwa.domain.ranking;

import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingResponseDto;
import java.time.LocalDateTime;
import java.util.List;

public interface RankingRepositoryCustom {
  public List<MonthRankingResponseDto> findMonthRanking(Long teamId, LocalDateTime startTime);
}
