package com.unsolvedwa.unsolvedwa.user;

import com.unsolvedwa.unsolvedwa.BaseTimeEntity;
import com.unsolvedwa.unsolvedwa.problem.Problem;
import com.unsolvedwa.unsolvedwa.ranking.Ranking;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class User extends BaseTimeEntity {
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

    public void setBasucInfoUser(String bojId, Boolean isMember) {
        this.bojId = bojId;
        this.isMember = true;
    }

    public void addSovlingInfo(Long score) {
        this.score += score;
        this.solvingCount++;
    }
}
