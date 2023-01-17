package com.unsolvedwa.unsolvedwa.domain.team;

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
public class Team extends BaseTimeEntity {

  @Id
  @GeneratedValue
  @Column(name = "team_id")
  private Long id;

  private String name;

  @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
  private List<UserTeam> userTeams;

  @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
  private List<ProblemTeam> problemTeams;

  @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
  private List<Ranking> rankings;

  protected Team() {
  }

  public Team(String name) {
    this.name = name;
  }

  public void setIdForTest(Long id) {
    this.id = id;
  }
}
