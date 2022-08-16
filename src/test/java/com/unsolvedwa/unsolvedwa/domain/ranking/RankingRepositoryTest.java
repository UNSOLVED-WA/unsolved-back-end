package com.unsolvedwa.unsolvedwa.domain.ranking;

import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingTop10ResponseDto;
import com.unsolvedwa.unsolvedwa.domain.team.Team;
import com.unsolvedwa.unsolvedwa.domain.team.TeamRepository;
import com.unsolvedwa.unsolvedwa.domain.user.User;
import com.unsolvedwa.unsolvedwa.domain.user.UserRepository;
import com.unsolvedwa.unsolvedwa.domain.userteam.UserTeam;
import com.unsolvedwa.unsolvedwa.domain.userteam.UserTeamRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@TestInstance(Lifecycle.PER_CLASS)
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

  @BeforeAll
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
  @TestInstance(Lifecycle.PER_CLASS)
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
        ranking.increaseScore(i + 1L);
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

      setTestData(numOfUsers, numOfTeamUsers, numOfRankUsers, useSameScore);

      //when
      List<MonthRankingTop10ResponseDto> responseData = rankingRepository.findMonthRankingTop10(team.getId(), curMonth);

      //then
      Assertions.assertThat(responseData).hasSize(numOfRankUsers);

      for (int i = 0; i < numOfRankUsers; i++){
        Assertions.assertThat(responseData.get(i).getBojId()).isEqualTo("user"+(numOfRankUsers - i));
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

      setTestData(numOfUsers, numOfTeamUsers, numOfRankUsers, useSameScore);

      //when
      List<MonthRankingTop10ResponseDto> responseData = rankingRepository.findMonthRankingTop10(team.getId(), curMonth);

      //then
      Assertions.assertThat(responseData).hasSize(numOfRankUsers);

      for (int i = 0; i < numOfRankUsers; i++){
        Assertions.assertThat(responseData.get(i).getBojId()).isEqualTo("user" + (i + 1));
        Assertions.assertThat(responseData.get(i).getScore()).isEqualTo(numOfUsers + 0L);
      }
    }

    @Test
    @Transactional
    void LessUserAtRank() throws Exception {
      //given
      Integer numOfUsers = 20;
      Integer numOfTeamUsers = 20;
      Integer numOfRankUsers = 10;
      Boolean useSameScore = Boolean.FALSE;

      setTestData(numOfUsers, numOfTeamUsers, numOfRankUsers, useSameScore);

      //when
      List<MonthRankingTop10ResponseDto> responseData = rankingRepository.findMonthRankingTop10(team.getId(), curMonth);

      //then
      Assertions.assertThat(responseData).hasSize(numOfRankUsers);

      for (int i = 0; i < numOfRankUsers; i++){
        Assertions.assertThat(responseData.get(i).getBojId()).isEqualTo("user"+(numOfRankUsers - i));
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

      setTestData(numOfUsers, numOfTeamUsers, numOfRankUsers, useSameScore);

      //when
      List<MonthRankingTop10ResponseDto> responseData = rankingRepository.findMonthRankingTop10(team.getId(), curMonth);

      //then
      Assertions.assertThat(responseData).hasSize(numOfRankUsers);

      for (int i = 0; i < numOfRankUsers; i++){
        Assertions.assertThat(responseData.get(i).getBojId()).isEqualTo("user"+(numOfRankUsers - i));
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

      setTestData(numOfUsers, numOfTeamUsers, numOfRankUsers, useSameScore);

      //when
      List<MonthRankingTop10ResponseDto> responseData = rankingRepository.findMonthRankingTop10(team.getId(), curMonth);

      //then
      Assertions.assertThat(responseData).hasSize(numOfRankUsers);

      for (int i = 0; i < numOfRankUsers; i++){
        Assertions.assertThat(responseData.get(i).getBojId()).isEqualTo("user"+(numOfRankUsers - i));
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

      setTestData(numOfUsers, numOfTeamUsers, numOfRankUsers, useSameScore);

      //when
      List<MonthRankingTop10ResponseDto> responseData = rankingRepository.findMonthRankingTop10(team.getId(), curMonth);

      //then
      Assertions.assertThat(responseData).hasSize(numOfRankUsers);

      for (int i = 0; i < numOfRankUsers; i++){
        Assertions.assertThat(responseData.get(i).getBojId()).isEqualTo("user"+(numOfRankUsers - i));
        Assertions.assertThat(responseData.get(i).getScore()).isEqualTo((numOfRankUsers - i) + 0L);
      }
    }
  }
}