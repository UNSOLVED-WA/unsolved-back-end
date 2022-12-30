package com.unsolvedwa.unsolvedwa.domain.problem;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;

import com.unsolvedwa.unsolvedwa.domain.problem.dto.ProblemResponseDto;
import com.unsolvedwa.unsolvedwa.domain.problem.dto.SolvingProblemResponseDto;
import com.unsolvedwa.unsolvedwa.domain.problemteam.ProblemTeam;
import com.unsolvedwa.unsolvedwa.domain.problemteam.ProblemTeamRepository;
import com.unsolvedwa.unsolvedwa.domain.ranking.Ranking;
import com.unsolvedwa.unsolvedwa.domain.ranking.RankingRepository;
import com.unsolvedwa.unsolvedwa.domain.team.Team;
import com.unsolvedwa.unsolvedwa.domain.team.TeamRepository;
import com.unsolvedwa.unsolvedwa.domain.user.User;
import com.unsolvedwa.unsolvedwa.domain.user.UserRepository;
import com.unsolvedwa.unsolvedwa.domain.userteam.UserTeam;
import com.unsolvedwa.unsolvedwa.domain.userteam.UserTeamRepository;
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

  @Mock
  UserRepository userRepository;

  @Mock
  UserTeamRepository userTeamRepository;

  @Mock
  ProblemTeamRepository problemTeamRepository;

  @Mock
  RankingRepository rankingRepository;

  Long teamId;
  Long tier;
  Optional<Team> optionalTeam;

  @BeforeEach
  void beforeEach() throws Exception {
    this.teamId = 1L;
    this.tier = 1L;
  }

  @Nested
  class SolveProblem{
    Long userId;
    Long problemNumber;
    User user;
    Problem problem;
    String bojId;
    String title;
    Long tier;

    @Nested
    class UserIsInvalid{
      @BeforeEach
      void beforeEach() throws Exception {
        userId = 1L;
        bojId = "user";
        problemNumber = 1L;

        Optional<User> optionalUser = Optional.empty();
        doReturn(optionalUser).when(userRepository).findByBojId(bojId);
      }

      @Test
      void fail() throws Exception {
        //given
        //when
        //then
        assertThrows(NotFoundException.class, ()->{
          problemService.solveProblem(bojId, problemNumber);
        });
      }
    }

    @Nested
    class ProblemIsInvalid{
      @BeforeEach
      void beforeEach() throws Exception {
        userId = 1L;
        problemNumber = 1L;
        bojId = "user";
        user = new User(bojId);

        Optional<User> optionalUser = Optional.of(user);
        doReturn(optionalUser).when(userRepository).findByBojId(bojId);

        Optional<Problem> optionalProblem = Optional.empty();
        doReturn(optionalProblem).when(problemRepository).findByProblemNumber(problemNumber);
      }

      @Test
      void fail() throws Exception {
        //given
        //when
        //then
        assertThrows(NotFoundException.class, ()->{
          problemService.solveProblem(bojId, problemNumber);
        });
      }
    }

    @Nested
    class Success{
      List<Team> teamList;
      List<UserTeam> userTeamList;

      @BeforeEach
      void beforeEach() throws Exception {
        userId = 1L;
        problemNumber = 1L;
        bojId = "user";
        title = "problem";
        tier = 1L;
        user = new User(bojId);
        problem = new Problem(problemNumber, title, tier);
        teamList = new ArrayList<>();
        userTeamList = new ArrayList<>();

        for (int i = 0 ; i < 2; i++){
          Team team = new Team("team" + i);
          teamList.add(team);

          UserTeam userTeam = new UserTeam(team, user);
          userTeamList.add(userTeam);
        }

        if (teamList.size() > 0) {
          lenient().doReturn(userTeamList.get(0)).when(userTeamRepository).save(any());
        }

        Optional<User> optionalUser = Optional.of(user);
        doReturn(optionalUser).when(userRepository).findByBojId(bojId);

        Optional<Problem> optionalProblem = Optional.of(problem);
        doReturn(optionalProblem).when(problemRepository).findByProblemNumber(problemNumber);

        doReturn(userTeamList).when(userTeamRepository).findAllByUser(user);

        Optional<Ranking> optionalRanking = Optional.empty();
        lenient().doReturn(optionalRanking).when(rankingRepository).findByTeamAndUserAndCreateAtAfter(any(), any(), any());

        Ranking ranking = new Ranking(user, teamList.get(0));
        lenient().doReturn(ranking).when(rankingRepository).save(any());
      }

      @Test
      void AlreadySolvingZero() throws Exception {
        //given
        //when
        List<ProblemTeam> problemTeamList = new ArrayList<>();
        User otherUser = new User("user2");
        Long alreadySolved = 0L;
        Long numOfTeam = 2L;

        for (int i = 0; i < alreadySolved; i++){
          ProblemTeam problemTeam = new ProblemTeam(problem, teamList.get(i), otherUser);
          problemTeamList.add(problemTeam);
        }

        if (problemTeamList.size() > 0) {
          doReturn(problemTeamList.get(0)).when(problemTeamRepository).save(any());
        }

        doReturn(problemTeamList).when(problemTeamRepository).findAllByUserAndProblem(user, problem);
        doReturn(Optional.empty()).when(problemTeamRepository).findByProblemAndTeam(any(), any());

        //then
        List<SolvingProblemResponseDto> solvingProblemResponseDtoList = problemService.solveProblem(bojId, problemNumber);
        Assertions.assertThat(solvingProblemResponseDtoList).hasSize(numOfTeam.intValue() - alreadySolved.intValue());
      }

      @Test
      void AlreadySolvingOne() throws Exception {
        //given
        //when
        List<ProblemTeam> problemTeamList = new ArrayList<>();
        User otherUser = new User("user2");
        Long alreadySolved = 1L;
        Long numOfTeam = 2L;

        for (int i = 0; i < alreadySolved; i++){
          ProblemTeam problemTeam = new ProblemTeam(problem, teamList.get(i), otherUser);
          problemTeamList.add(problemTeam);
        }

        doReturn(problemTeamList.get(0)).when(problemTeamRepository).save(any());
        doReturn(problemTeamList).when(problemTeamRepository).findAllByUserAndProblem(user, problem);
        doReturn(Optional.of(problemTeamList.get(0))).when(problemTeamRepository).findByProblemAndTeam(problem, teamList.get(0));
        doReturn(Optional.empty()).when(problemTeamRepository).findByProblemAndTeam(problem, teamList.get(1));

        //then
        List<SolvingProblemResponseDto> solvingProblemResponseDtoList = problemService.solveProblem(bojId, problemNumber);
        Assertions.assertThat(solvingProblemResponseDtoList).hasSize(numOfTeam.intValue() - alreadySolved.intValue());
      }

      @Test
      void AlreadySolvingTwo() throws Exception {
        //given
        //when
        List<ProblemTeam> problemTeamList = new ArrayList<>();
        User otherUser = new User("user2");
        Long alreadySolved = 2L;
        Long numOfTeam = 2L;

        for (int i = 0; i < alreadySolved; i++){
          ProblemTeam problemTeam = new ProblemTeam(problem, teamList.get(i), otherUser);
          problemTeamList.add(problemTeam);
        }

//        if (problemTeamList.size() > 0) {
//          doReturn(problemTeamList.get(0)).when(problemTeamRepository).save(any());
//        }

        doReturn(problemTeamList).when(problemTeamRepository).findAllByUserAndProblem(user, problem);
//        doReturn(Optional.of(problemTeamList.get(0))).when(problemTeamRepository).findByProblemAndTeam(problem, teamList.get(0));
//        doReturn(Optional.of(problemTeamList.get(1))).when(problemTeamRepository).findByProblemAndTeam(problem, teamList.get(1));

        //then
        List<SolvingProblemResponseDto> solvingProblemResponseDtoList = problemService.solveProblem(bojId, problemNumber);
        Assertions.assertThat(solvingProblemResponseDtoList).hasSize(numOfTeam.intValue() - alreadySolved.intValue());
      }
    }
  }

  @Nested
  class FindUnsolvedProblemsByTeamAndTier{

    @Nested
    class TeamIsValid{
      Team team;
      Long problemId;
      Long problemNumber;
      String problemTitle;
      Long tier;

      @BeforeEach
      void beforeEach() throws Exception {
        LocalDateTime cur = LocalDateTime.now();

        problemId = 1L;
        problemNumber = 1L;
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
          ProblemResponseDto problemResponseDto = new ProblemResponseDto(problemId, problemNumber, problemTitle + i, tier);
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
