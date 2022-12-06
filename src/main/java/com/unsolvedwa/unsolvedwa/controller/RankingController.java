package com.unsolvedwa.unsolvedwa.controller;

import com.unsolvedwa.unsolvedwa.domain.ranking.RankingService;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.AllRankingResponseDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingHistoryResponseDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingRequestDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "RankingController")
@RequestMapping("/rankings")
@RequiredArgsConstructor
public class RankingController {

  private final RankingService rankingService;

/*  @Operation(description = "전체기간랭킹")
  @GetMapping(value = "/{teamId}")
  public ResponseEntity<List<Ranking>> getRanking(@Parameter @PathVariable Long teamId) {
    //TODO: service 구현하여 작성
    return ResponseEntity.ok(rankingService.findById());
  }*/

  @Operation(description = "전체랭킹")
  @GetMapping(value = "/{group_id}")
  public ResponseEntity<List<AllRankingResponseDto>> getRanking(@Parameter @PathVariable Long teamId) {
    try
    {
      return  ResponseEntity.ok(rankingService.AllRanking(teamId));
    }
    catch (NotFoundException e)
    {
      return ResponseEntity.notFound().build();
    }
  }

  @Operation(description = "월간랭킹")
  @GetMapping(value = "/month/{teamId}")
  public ResponseEntity<List<MonthRankingResponseDto>> getMonthRanking(@Parameter @PathVariable Long teamId) {
    MonthRankingRequestDto monthRankingRequestDto = new MonthRankingRequestDto(teamId);
    try
    {
      return ResponseEntity.ok(rankingService.findMonthRankingAtThisMonth(monthRankingRequestDto));
    }
    catch (NotFoundException e)
    {
      return ResponseEntity.notFound().build();
    }
  }

  @Operation(description = "월간랭킹 기록 조회")
  @GetMapping(value = "/month/history/{teamId}/{userId}")
  public ResponseEntity<List<MonthRankingHistoryResponseDto>> getMonthRanking(@Parameter @PathVariable Long teamId, @Parameter @PathVariable Long userId) {
    try
    {
      List<MonthRankingHistoryResponseDto> monthRankingHistoryResponseDtoList = rankingService.findRankingHistory(teamId, userId);
      if (monthRankingHistoryResponseDtoList.isEmpty()) {
        return ResponseEntity.noContent().build();
      }
      else {
        return ResponseEntity.ok(monthRankingHistoryResponseDtoList);
      }
    }
    catch (NotFoundException e)
    {
      return ResponseEntity.notFound().build();
    }
  }
}
