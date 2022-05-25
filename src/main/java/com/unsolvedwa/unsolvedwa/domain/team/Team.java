package com.unsolvedwa.unsolvedwa.domain.team;

import com.unsolvedwa.unsolvedwa.domain.BaseTimeEntity;
import com.unsolvedwa.unsolvedwa.domain.ranking.Ranking;
import com.unsolvedwa.unsolvedwa.domain.problem_team.ProblemTeam;
import com.unsolvedwa.unsolvedwa.domain.user_team.UserTeam;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Team extends BaseTimeEntity {

  @Id
  @GeneratedValue
  @Column(name = "group_id")
  private Long id;

  private String name;

  @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
  private List<UserTeam> userTeams;

  @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
  private List<ProblemTeam> problemTeams;

  @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
  private List<Ranking> rankings;
}
