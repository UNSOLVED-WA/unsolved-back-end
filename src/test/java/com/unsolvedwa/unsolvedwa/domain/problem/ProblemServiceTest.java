package com.unsolvedwa.unsolvedwa.domain.problem;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import com.unsolvedwa.unsolvedwa.domain.problem.dto.ProblemResponseDto;
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
public class ProblemServiceTest {

  @InjectMocks
  ProblemService problemService;

  @Mock
  TeamRepository teamRepository;

  @Mock
  ProblemRepository problemRepository;

  Long teamId;
  Long tier;
  Optional<Team> optionalTeam;

  @BeforeEach
  void beforeEach() throws Exception {
    this.teamId = 1L;
    this.tier = 1L;
  }

  @Nested
  class FindUnsolvedProblemsByTeamAndTier{

    @Nested
    class TeamIsValid{
      Team team;
      Long problemId;
      String problemTitle;
      Long tier;

      @BeforeEach
      void beforEach() throws Exception {
        LocalDateTime cur = LocalDateTime.now();

        problemId = 1L;
        problemTitle = "problem";
        tier = 1L;

        this.team = new Team("teamName");
        optionalTeam = Optional.of(team);
        doReturn(optionalTeam).when(teamRepository).findById(teamId);
      }

      @Test
      void Success() throws Exception {
        //given
        List<ProblemResponseDto> unsolvedProblemResponseDtoList = new ArrayList<>();
        for (int i = 1; i <= 10; i++)
        {
          ProblemResponseDto problemResponseDto = new ProblemResponseDto(problemId, problemTitle + i, tier);
          unsolvedProblemResponseDtoList.add(problemResponseDto);
        }

        //when
        doReturn(unsolvedProblemResponseDtoList).when(problemRepository).findUnsolvedProblemsByTeamAndTier(teamId, tier);
        List<ProblemResponseDto> responseDtoList = problemService.findUnsolvedProblemsByTeamAndTier(teamId, tier);

        // then
        Assertions.assertThat(responseDtoList).hasSize(10);

        for (int i = 0; i < 10; i++)
        {
          Assertions.assertThat(responseDtoList.get(i).getProblemId()).isEqualTo(
              unsolvedProblemResponseDtoList.get(i).getProblemId());
          Assertions.assertThat(responseDtoList.get(i).getTier()).isEqualTo(
              unsolvedProblemResponseDtoList.get(i).getTier());
        }
      }

      @Test
      void AllSolvedAtThisTier() throws Exception {
        //given
        List<ProblemResponseDto> unsolvedProblemResponseDtoList = new ArrayList<>();

        //when
        doReturn(unsolvedProblemResponseDtoList).when(problemRepository).findUnsolvedProblemsByTeamAndTier(teamId, tier);
        List<ProblemResponseDto> responseDtoList = problemService.findUnsolvedProblemsByTeamAndTier(teamId, tier);

        // then
        Assertions.assertThat(responseDtoList).isEmpty();
      }
    }

    @Nested
    class TeamIsInvalid{

      @BeforeEach
      void beforeEach() throws Exception {
        optionalTeam = Optional.empty();
        doReturn(optionalTeam).when(teamRepository).findById(teamId);
      }

      @Test
      void Fail() throws Exception {
        //given
        //when
        // then
        assertThrows(NotFoundException.class, ()->{
          problemService.findUnsolvedProblemsByTeamAndTier(teamId, tier);
        });
      }
    }
  }

}
