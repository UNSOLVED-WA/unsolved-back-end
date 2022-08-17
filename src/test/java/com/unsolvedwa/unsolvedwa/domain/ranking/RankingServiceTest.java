package com.unsolvedwa.unsolvedwa.domain.ranking;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingRequestDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingResponseDto;
import com.unsolvedwa.unsolvedwa.domain.team.Team;
import com.unsolvedwa.unsolvedwa.domain.team.TeamRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

@ExtendWith(MockitoExtension.class)
public class RankingServiceTest{

  @InjectMocks
  RankingService rankingService;

  @Mock
  TeamRepository teamRepository;

  @Mock
  RankingRepository rankingRepository;

  Long teamId;
  Optional<Team> optionalTeam;

  @BeforeEach
  void beforeEach() throws Exception {
    this.teamId = 1L;
  }

  @Nested
  class FindMonthRankingAtThisMonth {

    @Nested
    class TeamIdIsValid{

      Team team;
      LocalDateTime curMonth;


      @BeforeEach
      void beforEach() throws Exception {
        LocalDateTime cur = LocalDateTime.now();

        this.team = new Team("teamName");
        this.curMonth = LocalDateTime.of(cur.getYear(), cur.getMonth(), 1, 0, 0);
        optionalTeam = Optional.of(team);
        doReturn(optionalTeam).when(teamRepository).findById(teamId);
      }

      @Test
      void rankingIsValid() throws Exception {
        //given
        List<MonthRankingResponseDto> monthRankingResponseDtoList = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
          MonthRankingResponseDto monthRankingResponseDto = new MonthRankingResponseDto(team.getName(),"user"+i,10L - i);
          monthRankingResponseDtoList.add(monthRankingResponseDto);
        }
        MonthRankingRequestDto monthRankingRequestDto = new MonthRankingRequestDto(teamId);

        //when
        doReturn(monthRankingResponseDtoList).when(rankingRepository).findMonthRanking(teamId, curMonth);
        List<MonthRankingResponseDto> responseDtos = rankingService.findMonthRankingAtThisMonth(monthRankingRequestDto);

        //then
        Assertions.assertThat(responseDtos).hasSize(10);

        for (int i = 0; i < 10; i++)
        {
          Assertions.assertThat(responseDtos.get(i).getBojId()).isEqualTo(
              monthRankingResponseDtoList.get(i).getBojId());
          Assertions.assertThat(responseDtos.get(i).getScore()).isEqualTo(
              monthRankingResponseDtoList.get(i).getScore());
        }
      }

      @Test
      void rankingIsEmpty() throws Exception {
        //given
        List<MonthRankingResponseDto> monthRankingResponseDtoList = new ArrayList<>();
        MonthRankingRequestDto monthRankingRequestDto = new MonthRankingRequestDto(teamId);

        //when
        doReturn(monthRankingResponseDtoList).when(rankingRepository).findMonthRanking(teamId, curMonth);
        List<MonthRankingResponseDto> responseDtos = rankingService.findMonthRankingAtThisMonth(monthRankingRequestDto);

        // then
        Assertions.assertThat(responseDtos).isEmpty();
      }
    }

    @Nested
    class TeamIdIsNotValid{

      @BeforeEach
      void beforeEach() throws Exception {
        optionalTeam = Optional.empty();
        doReturn(optionalTeam).when(teamRepository).findById(teamId);
      }

      @Test
      void failTest() throws Exception {
        //given
        MonthRankingRequestDto monthRankingRequestDto = new MonthRankingRequestDto(teamId);

        //when
        // then
        assertThrows(NotFoundException.class, ()->{
          rankingService.findMonthRankingAtThisMonth(monthRankingRequestDto);
        });
      }
    }
  }
}
