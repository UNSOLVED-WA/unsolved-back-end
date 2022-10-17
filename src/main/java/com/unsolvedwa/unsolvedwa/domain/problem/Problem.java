package com.unsolvedwa.unsolvedwa.domain.problem;

import com.unsolvedwa.unsolvedwa.domain.BaseTimeEntity;
import com.unsolvedwa.unsolvedwa.domain.problemteam.ProblemTeam;
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
public class Problem extends BaseTimeEntity {

  @Id
  @GeneratedValue
  @Column(name = "problem_id")
  private Long id;

  @Column(unique = true)
  private Long problemNumber;

  private String title;

  private Long tier;

  @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
  private List<ProblemTeam> problemTeams;

  protected Problem() {
  }

  public Problem(Long problemNumber, String title, Long tier) {
    this.problemNumber = problemNumber;
    this.title = title;
    this.tier = tier;
  }
}
