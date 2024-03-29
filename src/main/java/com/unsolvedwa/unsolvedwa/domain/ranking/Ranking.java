package com.unsolvedwa.unsolvedwa.domain.ranking;

import com.unsolvedwa.unsolvedwa.domain.BaseTimeEntity;
import com.unsolvedwa.unsolvedwa.domain.team.Team;
import com.unsolvedwa.unsolvedwa.domain.user.User;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Ranking extends BaseTimeEntity {

  @Id
  @GeneratedValue
  @Column(name = "ranking_id")
  private Long id;

  private Long score;

  private Long monthRanking;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id")
  private Team team;

  protected Ranking() {
  }

  public Ranking(User user, Team team) {
    this.score = 0L;
    this.monthRanking = 0L;
    this.user = user;
    this.team = team;
  }

  public void changeCreateAtForTestData(LocalDateTime createAt)
  {
    this.setCreateAt(createAt);
  }
  public void changeMonthRankingForTestData(Long monthRanking) { this.monthRanking = monthRanking; }

  public void increaseScore(Long score) {
    this.score += score;
  }
}
