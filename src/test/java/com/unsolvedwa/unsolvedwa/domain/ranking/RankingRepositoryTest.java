package com.unsolvedwa.unsolvedwa.domain.ranking;

import com.unsolvedwa.unsolvedwa.domain.ranking.dto.AllRankingResponseDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingTop10ResponseDto;
import com.unsolvedwa.unsolvedwa.domain.team.Team;
import com.unsolvedwa.unsolvedwa.domain.team.TeamRepository;
import com.unsolvedwa.unsolvedwa.domain.user.User;
import com.unsolvedwa.unsolvedwa.domain.user.UserRepository;
import com.unsolvedwa.unsolvedwa.domain.userteam.UserTeam;
import com.unsolvedwa.unsolvedwa.domain.userteam.UserTeamRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
//@TestInstance(Lifecycle.PER_CLASS)
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

//  @BeforeAll
//  void setTimeData()
//  {
//    curMonthDateTime = LocalDateTime.now();
//    lastMonthDateTime = curMonthDateTime.minusMonths(1);
//    nextMonthDateTime = curMonthDateTime.plusMonths(1);
//    curMonth = LocalDateTime.of(curMonthDateTime.getYear(), curMonthDateTime.getMonth(), 1, 0, 0);
//    nextMonth = curMonth.plusMonths(1);
//    lastMonth = curMonth.minusMonths(1);
//  }

  @Nested
  @TestInstance(Lifecycle.PER_CLASS)
  @Rollback
  class findAllRanking {
    // 팀1에 유저 10명 동점자 없음
    // 팀2에 유저 10명 모두 동점자
    // 팀3에 유저가 없음

    List<Team> teams;
    List<User> users;
    List<UserTeam> userTeam;

    @BeforeAll
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
    @Transactional(readOnly = true)
    void findAllRanking_SuccessTest() throws Exception {
      //given
      //team1 test

      //when
      List<AllRankingResponseDto> responseData = rankingRepository.AllRanking(teams.get(0).getId());
      //then
      Assertions.assertThat(responseData).hasSize(10);

      for (int i = 0; i < 10; i++){
        Assertions.assertThat(responseData.get(i).getBojId()).isEqualTo("user"+(10 - i));
        Assertions.assertThat(responseData.get(i).getScore()).isEqualTo((10 - i) + 0L);
      }
    }

    @Test
    @Transactional(readOnly = true)
    void findAllRanking_AllSameScore() throws Exception {
      //given
      //team2 test

      //when
      List<AllRankingResponseDto> responseData = rankingRepository.AllRanking(teams.get(1).getId());
      //then
      Assertions.assertThat(responseData).hasSize(10);

      for (int i = 0; i < 10; i++){
        Assertions.assertThat(responseData.get(i).getBojId()).isEqualTo("user"+(i + 1l));
        Assertions.assertThat(responseData.get(i).getScore()).isEqualTo(10l);
      }
    }

    @Test
    @Transactional(readOnly = true)
    void findAllRanking_NoUserAtTeam() throws Exception {
      //given
      //team3 test

      //when
      List<AllRankingResponseDto> responseData = rankingRepository.AllRanking(teams.get(2).getId());
      //then
      Assertions.assertThat(responseData.isEmpty());
    }



  }
  @Nested
  @TestInstance(Lifecycle.PER_CLASS)
  class findMonthRankingTop10 {
    // 팀1에 유저 15명 동점자 없음
    // 팀2에 유저 15명 모두 동점자
    // 팀3 랭킹에 유저가 5명
    // 팀4 랭킹에 유저 없음
    // 팀5 에 유저 없음

    List<Team> teams;

    @BeforeAll
    void setTestData() {
      for (int i = 1; i <= 20; i++){
        User user = new User("user"+i);
        userRepository.save(user);
      }
      List<User> users = userRepository.findAll();

      for (int i = 1; i <= 5; i++){
        Team team = new Team("team" + i);
        teamRepository.save(team);
      }
      teams = teamRepository.findAll();


      for (int i = 0; i < 4; i++){
        for (int j = i; j < 15 + i; j++){
          UserTeam userTeam = new UserTeam(teams.get(i),users.get(j));
          userTeamRepository.save(userTeam);
        }
      }

      for (int i = 0; i < 3; i++){
        if (i == 0) {
          for (int j = i; j < 14 + i; j++) {
            Ranking ranking = new Ranking(users.get(j), teams.get(i));
            ranking.increaseScore(j + 1L);
            rankingRepository.save(ranking);
          }
          for (int j = i + 1; j < 15 + i; j++) {
            Ranking ranking = new Ranking(users.get(j), teams.get(i));
            ranking.increaseScore(j + 1L);
            rankingRepository.save(ranking);
            ranking.changeCreateAtForTestData(lastMonthDateTime);
            rankingRepository.save(ranking);
          }
        }
        else if (i == 1)
        {
          for (int j = i; j < 14 + i; j++) {
            Ranking ranking = new Ranking(users.get(j), teams.get(i));
            ranking.increaseScore(100L);
            rankingRepository.save(ranking);
          }
          for (int j = i + 1; j < 15 + i; j++) {
            Ranking ranking = new Ranking(users.get(j), teams.get(i));
            ranking.increaseScore(100L);
            rankingRepository.save(ranking);
            ranking.changeCreateAtForTestData(lastMonthDateTime);
            rankingRepository.save(ranking);
          }
        }
        else if (i== 2)
        {
          for (int j = i; j < 5 + i; j++) {
            Ranking ranking = new Ranking(users.get(j), teams.get(i));
            ranking.increaseScore(j + 1L);
            rankingRepository.save(ranking);
          }
          for (int j = i + 1; j < 15 + i; j++) {
            Ranking ranking = new Ranking(users.get(j), teams.get(i));
            ranking.increaseScore(j + 1L);
            rankingRepository.save(ranking);
            ranking.changeCreateAtForTestData(lastMonthDateTime);
            rankingRepository.save(ranking);
          }
        }
      }
    }

    @Test
    @Transactional(readOnly = true)
    void findMonthRankingTop10_SuccessTest() throws Exception {
      //given
      // team1 에 대한 테스트

      //when
      List<MonthRankingTop10ResponseDto> responseData = rankingRepository.findMonthRankingTop10(teams.get(0).getId(), curMonth);

      //then
      Assertions.assertThat(responseData).hasSize(10);

      for (int i = 0; i < 10; i++){
        Assertions.assertThat(responseData.get(i).getBojId()).isEqualTo("user"+(14 - i));
        Assertions.assertThat(responseData.get(i).getScore()).isEqualTo((14 - i) + 0L);
      }
    }

    @Test
    @Transactional(readOnly = true)
    void findMonthRankingTop10_SameScoreAtRanking() throws Exception {
      //given
      // team2에 대한 테스트

      //when
      List<MonthRankingTop10ResponseDto> responseData = rankingRepository.findMonthRankingTop10(teams.get(1).getId(), curMonth);

      // then
      Assertions.assertThat(responseData).hasSize(10);
      for (int i = 0; i < 10; i++) {
        Assertions.assertThat(responseData.get(i).getBojId()).isEqualTo("user" + (i + 2));
        Assertions.assertThat(responseData.get(i).getScore()).isEqualTo(100L);
      }
    }

    @Test
    @Transactional(readOnly = true)
    void findMonthRankingTop10_LessUserAtRanking() throws Exception {
      //given

      //when
      List<MonthRankingTop10ResponseDto> responseData = rankingRepository.findMonthRankingTop10(teams.get(2).getId(), curMonth);

      // then
      Assertions.assertThat(responseData).hasSize(5);
      for (int i = 0; i < 5; i++){
        Assertions.assertThat(responseData.get(i).getBojId()).isEqualTo("user"+(7 - i));
        Assertions.assertThat(responseData.get(i).getScore()).isEqualTo((7 - i) + 0L);
      }
    }

    @Test
    @Transactional(readOnly = true)
    void findMonthRankingTop10_NoUserAtTeam() throws Exception {
      //given

      //when
      List<MonthRankingTop10ResponseDto> responseData = rankingRepository.findMonthRankingTop10(teams.get(3).getId(), curMonth);

      // then
      Assertions.assertThat(responseData).isEmpty();
    }

    @Test
    @Transactional(readOnly = true)
    void findMonthRankingTop10_NoUserAtRanking() throws Exception {
      //given

      //when
      List<MonthRankingTop10ResponseDto> responseData = rankingRepository.findMonthRankingTop10(teams.get(4).getId(), curMonth);

      // then
      Assertions.assertThat(responseData).isEmpty();
    }

    @Test
    @Transactional(readOnly = true)
    void findMonthRankingTop10_NoTeamTest() throws Exception {
      //given

      //when
      List<MonthRankingTop10ResponseDto> responseData = rankingRepository.findMonthRankingTop10(teams.get(4).getId() + 10L, curMonth);

      // then
      Assertions.assertThat(responseData).isEmpty();
    }
  }
}
