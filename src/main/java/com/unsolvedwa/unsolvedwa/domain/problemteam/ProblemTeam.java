package com.unsolvedwa.unsolvedwa.domain.problemteam;

import com.unsolvedwa.unsolvedwa.domain.BaseTimeEntity;
import com.unsolvedwa.unsolvedwa.domain.problem.Problem;
import com.unsolvedwa.unsolvedwa.domain.team.Team;
import com.unsolvedwa.unsolvedwa.domain.user.User;
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
public class ProblemTeam extends BaseTimeEntity {

  @Id
  @GeneratedValue
  @Column(name = "problem_team_id")
  private Long id;

  private Long score;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "problem_id")
  private Problem problem;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id")
  private Team team;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  protected ProblemTeam() {
  }

  public ProblemTeam(Problem problem, Team team, User user) {
    this.problem = problem;
    this.team = team;
    this.user = user;
  }
}
