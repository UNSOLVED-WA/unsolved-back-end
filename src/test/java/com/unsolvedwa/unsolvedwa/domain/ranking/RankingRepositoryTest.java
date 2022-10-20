package com.unsolvedwa.unsolvedwa.domain.ranking;

import com.unsolvedwa.unsolvedwa.domain.ranking.dto.AllRankingResponseDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingResponseDto;
import com.unsolvedwa.unsolvedwa.domain.team.Team;
import com.unsolvedwa.unsolvedwa.domain.team.TeamRepository;
import com.unsolvedwa.unsolvedwa.domain.user.User;
import com.unsolvedwa.unsolvedwa.domain.user.UserRepository;
import com.unsolvedwa.unsolvedwa.domain.userteam.UserTeam;
import com.unsolvedwa.unsolvedwa.domain.userteam.UserTeamRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class RankingRepositoryTest {

  @Autowired
  UserRepository userRepository;
  @Autowired
  TeamRepository teamRepository;
  @Autowired
  UserTeamRepository userTeamRepository;
  @Autowired
  RankingRepository rankingRepository;

  LocalDateTime curMonthDateTime;
  LocalDateTime lastMonthDateTime;
  LocalDateTime nextMonthDateTime;
  LocalDateTime curMonth;
  LocalDateTime lastMonth;
  LocalDateTime nextMonth;

  @AfterEach
  void deleteAll(){
    rankingRepository.deleteAllInBatch();
    userTeamRepository.deleteAllInBatch();
    teamRepository.deleteAllInBatch();
    userRepository.deleteAllInBatch();
  }

  void setTimeData()
  {
    curMonthDateTime = LocalDateTime.now();
    lastMonthDateTime = curMonthDateTime.minusMonths(1);
    nextMonthDateTime = curMonthDateTime.plusMonths(1);
    curMonth = LocalDateTime.of(curMonthDateTime.getYear(), curMonthDateTime.getMonth(), 1, 0, 0);
    nextMonth = curMonth.plusMonths(1);
    lastMonth = curMonth.minusMonths(1);
  }

  @Nested
  class findAllRanking {
    // 팀1에 유저 10명 동점자 없음
    // 팀2에 유저 10명 모두 동점자
    // 팀3에 유저가 없음

    List<Team> teams;
    List<User> users;
    List<UserTeam> userTeam;

    @BeforeEach
    void setTestData() {
      for (int i = 1; i <= 20; i++){
        User user = new User("user"+i);
        userRepository.save(user);
      }
      for (int i = 1; i <= 3; i++){
        Team team = new Team("team"+i);
        teamRepository.save(team);
      }
      users = userRepository.findAll();
      teams = teamRepository.findAll();
      for (int i = 1; i <= 3; i++){
        if (i == 1){
          for (int j = 1; j <= 10; j++) {
            UserTeam userTeam = new UserTeam(teams.get(i - 1), users.get(j - 1));
            userTeam.increaseScore(j+ 0l);
            userTeamRepository.save(userTeam);
          }
        }
        if (i == 2){
          for (int j = 1; j <= 10; j++) {
            UserTeam userTeam = new UserTeam(teams.get(i - 1), users.get(j - 1));
            userTeam.increaseScore(10l);
            userTeamRepository.save(userTeam);
          }
        }
      }
    }

    @Test
    @Transactional
    void findAllRanking_SuccessTest() throws Exception {
      //given
      //team1 test
      //when
      List<AllRankingResponseDto> responseData = rankingRepository.AllRanking(teams.get(0).getId());
      //then
      Assertions.assertThat(responseData).hasSize(10);

      for (int i = 0; i < 10; i++){
        Assertions.assertThat(responseData.get(i).getScore()).isEqualTo((10 - i) + 0L);
      }
    }

    @Test
    @Transactional
    void findAllRanking_AllSameScore() throws Exception {
      //given
      //team2 test

      //when
      List<AllRankingResponseDto> responseData = rankingRepository.AllRanking(teams.get(1).getId());
      //then
      Assertions.assertThat(responseData).hasSize(10);

      for (int i = 0; i < 10; i++){
        Assertions.assertThat(responseData.get(i).getScore()).isEqualTo(10l);
      }
    }

    @Test
    @Transactional
    void findAllRanking_NoUserAtTeam() throws Exception {
      //given
      //team3 test

      //when
      List<AllRankingResponseDto> responseData = rankingRepository.AllRanking(teams.get(2).getId());
      //then
      Assertions.assertThat(responseData).isEmpty();
    }
  }
  
  @Nested
  class findMonthRanking {
    // 팀1에 유저 15명 동점자 없음
    // 팀2에 유저 15명 모두 동점자
    // 팀3 랭킹에 유저가 5명
    // 팀4 랭킹에 유저 없음
    // 팀5 에 유저 없음

    Team team;

    void setTestData(Integer numOfUsers, Integer numOfTeamUsers, Integer numOfRankUser, Boolean useSameScore) {
      for (int i = 0; i < numOfUsers; i++){
        User user = new User("user"+ (i + 1));
        userRepository.save(user);
      }
      List<User> users = userRepository.findAll();

      team = new Team("team");
      teamRepository.save(team);

      for (int i = 0; i < numOfTeamUsers; i++){
        UserTeam userTeam = new UserTeam(team, users.get(i));
        userTeamRepository.save(userTeam);
      }

      for (int i = 0; i < numOfRankUser; i++) {
        Ranking ranking = new Ranking(users.get(i), team);
        if (useSameScore) {
          ranking.increaseScore(numOfUsers + 0L);
        }
        else {
          ranking.increaseScore(i + 1L);
        }
        rankingRepository.save(ranking);
      }

      for (int i = 0; i < numOfRankUser; i++) {
        Ranking ranking = new Ranking(users.get(i), team);
        if (useSameScore) {
          ranking.increaseScore(numOfUsers + 0L);
        }
        else {
          ranking.increaseScore(i + 1L);
        }
        rankingRepository.save(ranking);
        ranking.changeCreateAtForTestData(lastMonthDateTime);
        rankingRepository.save(ranking);
      }
    }

    @Test
    @Transactional
    void Success() throws Exception {
      //given
      Integer numOfUsers = 20;
      Integer numOfTeamUsers = 20;
      Integer numOfRankUsers = 20;
      Boolean useSameScore = Boolean.FALSE;

      setTimeData();
      setTestData(numOfUsers, numOfTeamUsers, numOfRankUsers, useSameScore);

      //when
      List<MonthRankingResponseDto> responseData = rankingRepository.findMonthRanking(team.getId(), curMonth);

      //then
      Assertions.assertThat(responseData).hasSize(numOfRankUsers);

      for (int i = 0; i < numOfRankUsers; i++){
        System.out.println("responseData.get(i).getBojId() = " + responseData.get(i).getBojId());
        System.out.println("user = " + "user"+(numOfRankUsers - i));
        System.out.println("responseData.get(i).getScore() = " + responseData.get(i).getScore());
        System.out.println("((numOfRankUsers - i) + 0L) = " + ((numOfRankUsers - i) + 0L));
      }
      
      for (int i = 0; i < numOfRankUsers; i++){
        Assertions.assertThat(responseData.get(i).getScore()).isEqualTo((numOfRankUsers - i) + 0L);
      }
    }

    @Test
    @Transactional
    void SameScore() throws Exception {
      //given
      Integer numOfUsers = 20;
      Integer numOfTeamUsers = 20;
      Integer numOfRankUsers = 20;
      Boolean useSameScore = Boolean.TRUE;

      setTimeData();
      setTestData(numOfUsers, numOfTeamUsers, numOfRankUsers, useSameScore);

      //when
      List<MonthRankingResponseDto> responseData = rankingRepository.findMonthRanking(team.getId(), curMonth);

      //then
      Assertions.assertThat(responseData).hasSize(numOfRankUsers);
    }

    @Test
    @Transactional
    void LessUserAtRank() throws Exception {
      //given
      Integer numOfUsers = 20;
      Integer numOfTeamUsers = 20;
      Integer numOfRankUsers = 10;
      Boolean useSameScore = Boolean.FALSE;

      setTimeData();
      setTestData(numOfUsers, numOfTeamUsers, numOfRankUsers, useSameScore);

      //when
      List<MonthRankingResponseDto> responseData = rankingRepository.findMonthRanking(team.getId(), curMonth);

      //then
      Assertions.assertThat(responseData).hasSize(numOfRankUsers);

      for (int i = 0; i < numOfRankUsers; i++){
        Assertions.assertThat(responseData.get(i).getScore()).isEqualTo((numOfRankUsers - i) + 0L);
      }
    }

    @Test
    @Transactional
    void LessUserAtTeam() throws Exception {
      //given
      Integer numOfUsers = 20;
      Integer numOfTeamUsers = 10;
      Integer numOfRankUsers = 10;
      Boolean useSameScore = Boolean.FALSE;

      setTimeData();
      setTestData(numOfUsers, numOfTeamUsers, numOfRankUsers, useSameScore);

      //when
      List<MonthRankingResponseDto> responseData = rankingRepository.findMonthRanking(team.getId(), curMonth);

      //then
      Assertions.assertThat(responseData).hasSize(numOfRankUsers);

      for (int i = 0; i < numOfRankUsers; i++){
        Assertions.assertThat(responseData.get(i).getScore()).isEqualTo((numOfRankUsers - i) + 0L);
      }
    }

    @Test
    @Transactional
    void NoUserAtRanking() throws Exception {
      //given
      Integer numOfUsers = 20;
      Integer numOfTeamUsers = 10;
      Integer numOfRankUsers = 0;
      Boolean useSameScore = Boolean.FALSE;

      setTimeData();
      setTestData(numOfUsers, numOfTeamUsers, numOfRankUsers, useSameScore);

      //when
      List<MonthRankingResponseDto> responseData = rankingRepository.findMonthRanking(team.getId(), curMonth);

      //then
      Assertions.assertThat(responseData).hasSize(numOfRankUsers);

      for (int i = 0; i < numOfRankUsers; i++){
        Assertions.assertThat(responseData.get(i).getScore()).isEqualTo((numOfRankUsers - i) + 0L);
      }
    }

    @Test
    @Transactional
    void findMonthRankingTop10_NoTeamTest() throws Exception {
      //given
      Integer numOfUsers = 20;
      Integer numOfTeamUsers = 0;
      Integer numOfRankUsers = 0;
      Boolean useSameScore = Boolean.FALSE;

      setTimeData();
      setTestData(numOfUsers, numOfTeamUsers, numOfRankUsers, useSameScore);

      //when
      List<MonthRankingResponseDto> responseData = rankingRepository.findMonthRanking(team.getId(), curMonth);

      //then
      Assertions.assertThat(responseData).hasSize(numOfRankUsers);

      for (int i = 0; i < numOfRankUsers; i++){
        Assertions.assertThat(responseData.get(i).getScore()).isEqualTo((numOfRankUsers - i) + 0L);
      }
    }
  }
}