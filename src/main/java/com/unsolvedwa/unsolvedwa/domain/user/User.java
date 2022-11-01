package com.unsolvedwa.unsolvedwa.domain.user;

import com.unsolvedwa.unsolvedwa.domain.BaseTimeEntity;
import com.unsolvedwa.unsolvedwa.domain.problemteam.ProblemTeam;
import com.unsolvedwa.unsolvedwa.domain.ranking.Ranking;
import com.unsolvedwa.unsolvedwa.domain.userteam.UserTeam;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue
  @Column(name = "user_id")
  private Long id;

  private String bojId;

  private Long solvingCount;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<ProblemTeam> problemTeams;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Ranking> rankings;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<UserTeam> userTeams;

  protected User() {
  }

  public User(String bojId) {
    this.bojId = bojId;
    this.solvingCount = 0L;
  }
}
