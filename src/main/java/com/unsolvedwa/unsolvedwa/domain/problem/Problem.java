package com.unsolvedwa.unsolvedwa.domain.problem;

import com.unsolvedwa.unsolvedwa.domain.BaseTimeEntity;
import com.unsolvedwa.unsolvedwa.domain.problem_team.ProblemTeam;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Problem extends BaseTimeEntity {

  @Id
  @GeneratedValue
  @Column(name = "problem_id")
  private Long id;

  private String title;

  private Long tier;

  @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
  private List<ProblemTeam> problemTeams;

  public void setBasicInfo(String title, Long tier) {
    this.title = title;
    this.tier = tier;
  }
}
