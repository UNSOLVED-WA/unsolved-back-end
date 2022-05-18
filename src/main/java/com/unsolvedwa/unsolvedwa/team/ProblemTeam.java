package com.unsolvedwa.unsolvedwa.team;

import com.unsolvedwa.unsolvedwa.BaseTimeEntity;
import com.unsolvedwa.unsolvedwa.problem.Problem;
import com.unsolvedwa.unsolvedwa.user.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ProblemTeam extends BaseTimeEntity {
    @Id @GeneratedValue
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
}
