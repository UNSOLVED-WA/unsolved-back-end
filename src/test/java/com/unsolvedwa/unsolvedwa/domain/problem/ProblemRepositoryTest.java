package com.unsolvedwa.unsolvedwa.domain.problem;

import com.unsolvedwa.unsolvedwa.domain.problem.dto.ProblemResponseDto;
import com.unsolvedwa.unsolvedwa.domain.problemteam.ProblemTeamRepository;
import com.unsolvedwa.unsolvedwa.domain.team.Team;
import com.unsolvedwa.unsolvedwa.domain.team.TeamRepository;
import com.unsolvedwa.unsolvedwa.domain.user.User;
import com.unsolvedwa.unsolvedwa.domain.user.UserRepository;
import com.unsolvedwa.unsolvedwa.domain.userteam.UserTeam;
import com.unsolvedwa.unsolvedwa.domain.userteam.UserTeamRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ProblemRepositoryTest {

  @Autowired
  ProblemRepository problemRepository;
  @Autowired
  TeamRepository teamRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  UserTeamRepository userTeamRepository;
  @Autowired
  ProblemTeamRepository problemTeamRepository;
  @Autowired
  ProblemService problemService;

  @Nested
  class FindUnsolvedProblemByTeamIdAndTier{
    List<User> users;
    List<Team> teams;
    List<Problem> problems;

    @AfterEach
    public void deleteAll(){
      problemTeamRepository.deleteAllInBatch();
      userTeamRepository.deleteAllInBatch();
      userRepository.deleteAllInBatch();
      teamRepository.deleteAllInBatch();
      problemRepository.deleteAllInBatch();
    }

    public void setTestData(Long numOfProblems, Long numOfTeams, Long numOfUsers, Long numOfSolvedProblemByEachUser, Long tier)
    {
      for (int i = 0; i < numOfUsers; i++) {
        User user = new User("user"+ (i + 1));
        userRepository.save(user);
      }
      users = userRepository.findAll();

      for (int i = 0; i < numOfTeams; i++) {
        Team team = new Team("team" + (i + 1));
        teamRepository.save(team);
      }
      teams = teamRepository.findAll();

      for (int i = 0; i < numOfTeams; i++) {
        for (int j = 0; j < numOfUsers - i; j++) {
          UserTeam userTeam = new UserTeam(teams.get(i), users.get(j));
          userTeamRepository.save(userTeam);
        }
      }

      for (int i = 0; i < numOfProblems; i++) {
        Problem problem = new Problem(0L + i, "problem" + i, tier);
        problem = problemRepository.save(problem);
      }
      problems = problemRepository.findAll();

      for (int i = 0; i < numOfProblems && numOfSolvedProblemByEachUser > 0; i++) {
        if (i/numOfSolvedProblemByEachUser.intValue() >= numOfUsers){
          break;
        }
        Problem problem = problems.get(i);
        User solvingUser = users.get(i/numOfSolvedProblemByEachUser.intValue());
        try {
          problemService.solveProblem(solvingUser.getId(), problem.getProblemNumber());
        }
        catch (NotFoundException notFoundException) {
        }
      }

    }

    public void AssertProblemDto(Long numOfProblems, Long numOfTeams, Long numOfUsers, Long numOfSolvedProblemByEachUser, Long tier){
      for (int i = 0; i < numOfTeams; i++)
      {
        List<ProblemResponseDto> unsolvedProblemResponseDtoList = problemRepository.findUnsolvedProblemsByTeamAndTier(teams.get(i).getId(), tier);
        Long curNumOfUnsolvedProblem = numOfProblems - numOfTeams * numOfSolvedProblemByEachUser + i * numOfSolvedProblemByEachUser;
        Assertions.assertThat(unsolvedProblemResponseDtoList).hasSize(curNumOfUnsolvedProblem.intValue());
      }
    }

    //문제 10문제
    //팀 5개
    //유저 5명
    // 팀 1 유저 5명, 팀 2 유저 4명, 팀 3 유저 3명, 팀 4 유저 2명, 팀 5 유저 1명
    //유저들이 각 1문제씩 총 5문제 품
    //기대 리턴 : 팀 1 안푼 문제 5, 팀 2 안푼 문제 6, 팀 3 안푼 문제 7, 팀 4 안푼 문제 8, 팀 5 안푼 문제 9

    @Test
    void success() throws Exception {
      //given
      Long numOfProblems = 10L;
      Long numOfTeams = 5L;
      Long numOfUsers = 5L;
      Long numOfSolvedProblemByEachUser = 1L;
      Long tier = 1L;

      setTestData(numOfProblems, numOfTeams, numOfUsers, numOfSolvedProblemByEachUser, tier);

      //when
      // then
      AssertProblemDto(numOfProblems, numOfTeams, numOfUsers, numOfSolvedProblemByEachUser, tier);
    }

    @Test
    void zeroSolve() throws Exception {
      //given
      Long numOfProblems = 10L;
      Long numOfTeams = 5L;
      Long numOfUsers = 5L;
      Long numOfSolvedProblemByEachUser = 0L;
      Long tier = 1L;

      setTestData(numOfProblems, numOfTeams, numOfUsers, numOfSolvedProblemByEachUser, tier);

      //when
      // then
      AssertProblemDto(numOfProblems, numOfTeams, numOfUsers, numOfSolvedProblemByEachUser, tier);
    }

    @Test
    void team1AllSolve() throws Exception {
      //given
      Long numOfProblems = 10L;
      Long numOfTeams = 5L;
      Long numOfUsers = 5L;
      Long numOfSolvedProblemByEachUser = 2L;
      Long tier = 1L;

      setTestData(numOfProblems, numOfTeams, numOfUsers, numOfSolvedProblemByEachUser, tier);

      //when
      // then
      AssertProblemDto(numOfProblems, numOfTeams, numOfUsers, numOfSolvedProblemByEachUser, tier);
    }

    @Test
    void zeroUser() throws Exception {
      //given
      Long numOfProblems = 10L;
      Long numOfTeams = 5L;
      Long numOfUsers = 0L;
      Long numOfSolvedProblemByEachUser = 0L;
      Long tier = 1L;

      setTestData(numOfProblems, numOfTeams, numOfUsers, numOfSolvedProblemByEachUser, tier);

      //when
      // then
      AssertProblemDto(numOfProblems, numOfTeams, numOfUsers, numOfSolvedProblemByEachUser, tier);
    }

    @Test
    void zeroProblem() throws Exception {
      //given
      Long numOfProblems = 0L;
      Long numOfTeams = 5L;
      Long numOfUsers = 5L;
      Long numOfSolvedProblemByEachUser = 0L;
      Long tier = 1L;

      setTestData(numOfProblems, numOfTeams, numOfUsers, numOfSolvedProblemByEachUser, tier);

      //when
      // then
      AssertProblemDto(numOfProblems, numOfTeams, numOfUsers, numOfSolvedProblemByEachUser, tier);
    }
  }
}
