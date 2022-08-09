package com.unsolvedwa.unsolvedwa.domain.ranking;

import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingTop10ResponseDto;
import java.time.LocalDateTime;
import java.util.List;

public interface RankingRepositoryCustom {
  public List<MonthRankingTop10ResponseDto> findMonthRankingTop10(Long teamId, LocalDateTime startTime);
}
