package com.unsolvedwa.unsolvedwa.domain.ranking;

import com.unsolvedwa.unsolvedwa.domain.ranking.dto.AllRankingResponseDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingHistoryResponseDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingResponseDto;
import java.time.LocalDateTime;
import java.util.List;

public interface RankingRepositoryCustom {
  public List<MonthRankingResponseDto> findMonthRanking(Long teamId, LocalDateTime startTime);
  public List<MonthRankingResponseDto> findMonthRankingByTeamAndUser(Long teamId, Long userId, LocalDateTime startTime);
  public List<MonthRankingHistoryResponseDto> findMonthRankingHistoryByTeamAndUser(Long teamId, Long userId);
  public List<AllRankingResponseDto> AllRanking(Long teamId);
}
