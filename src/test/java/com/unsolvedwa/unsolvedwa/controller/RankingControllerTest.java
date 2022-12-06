package com.unsolvedwa.unsolvedwa.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.unsolvedwa.unsolvedwa.domain.ranking.RankingService;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingHistoryResponseDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingRequestDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingResponseDto;
import com.unsolvedwa.unsolvedwa.domain.team.Team;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RankingController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class RankingControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  RankingService rankingService;

  Long teamId;
  Long userId;
  Team team;

  @BeforeEach
  void beforeEach() throws Exception {
    this.teamId = 1L;
    this.userId = 1L;

    team = new Team("teamName");
  }

  @Nested
  class MonthRankingTest{
    @Test
    void successTest() throws Exception {
      //given
      List<MonthRankingResponseDto> monthRankingResponseDtoList = new ArrayList<>();
      for (int i = 0; i < 10; i++)
      {
        LocalDateTime curTime = LocalDateTime.now();
        MonthRankingResponseDto monthRankingResponseDto = new MonthRankingResponseDto(team.getName(),"user"+i,10L - i, curTime);
        monthRankingResponseDtoList.add(monthRankingResponseDto);
      }
      MonthRankingRequestDto monthRankingRequestDto = new MonthRankingRequestDto(teamId);
      given(rankingService.findMonthRankingAtThisMonth(any())).willReturn(
          monthRankingResponseDtoList);

      //when
      // then
      mockMvc.perform(get("/rankings/month/1"))
          .andExpect(status().isOk());
    }

    @Test
    void failTest() throws Exception {
      //given
      MonthRankingRequestDto monthRankingRequestDto = new MonthRankingRequestDto(teamId);
      given(rankingService.findMonthRankingAtThisMonth(any())).willThrow(new NotFoundException());

      //when
      // then
      mockMvc.perform(get("/rankings/month/1"))
          .andExpect(status().isNotFound());
    }
  }

  @Nested
  class MonthRankingHistoryTest{


    @Test
    void success() throws Exception {
      //given
      List<MonthRankingHistoryResponseDto> responseDtoList = new ArrayList<>();
      LocalDateTime curTime = LocalDateTime.now();

      for (int i = 0; i < 10; i++)
      {
        LocalDateTime rankingTime = curTime.minusMonths(i + 0L);
        MonthRankingHistoryResponseDto responseDto = new MonthRankingHistoryResponseDto("team" + i, "bojId" + i, i + 1L, i + 1L, rankingTime);
        responseDtoList.add(responseDto);
      }

      given(rankingService.findRankingHistory(any(), any())).willReturn(responseDtoList);

      //when
      // then
      mockMvc.perform(get("/rankings/month/history/" + teamId + "/" + userId))
          .andExpect(status().isOk());
    }

    @Test
    void noContent() throws Exception {
      //given
      List<MonthRankingHistoryResponseDto> responseDtoList = new ArrayList<>();
      given(rankingService.findRankingHistory(any(), any())).willReturn(responseDtoList);
      //when
      // then
      mockMvc.perform(get("/rankings/month/history/" + teamId + "/" + userId))
          .andExpect(status().isNoContent());
    }

    @Test
    void throwNotFound() throws Exception {
      //given
      given(rankingService.findRankingHistory(any(), any())).willThrow(new NotFoundException());

      //when
      // then
      mockMvc.perform(get("/rankings/month/history/" + teamId + "/" + userId))
          .andExpect(status().isNotFound());
    }
  }
}
