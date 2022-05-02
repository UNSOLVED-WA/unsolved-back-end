package com.unsolvedwa.unsolvedwa.user;

import com.unsolvedwa.unsolvedwa.BaserTimeEntity;
import com.unsolvedwa.unsolvedwa.problem.Problem;
import com.unsolvedwa.unsolvedwa.ranking.Ranking;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class User extends BaserTimeEntity {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String bojId;

    private Boolean isMember;

    private Long solvingCount;

    private Long score;

    @OneToMany
    @JoinColumn(name = "problem_id")
    private List<Problem> problems;
    @OneToMany
    @JoinColumn(name = "ranking_id")
    private List<Ranking> rankings;

    public void setBasicInfoUser(String bojId, Boolean isMember) {
        this.bojId = bojId;
        this.isMember = true;
    }

    public void addSolvingInfo(Long score) {
        this.score += score;
        this.solvingCount++;
    }
}
