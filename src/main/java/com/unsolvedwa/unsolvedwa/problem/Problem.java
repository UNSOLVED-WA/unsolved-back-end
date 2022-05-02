package com.unsolvedwa.unsolvedwa.problem;

import com.unsolvedwa.unsolvedwa.BaseTimeEntity;
import com.unsolvedwa.unsolvedwa.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Problem extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "problem_id")
    private Long id;

    private String title;

    private Long tier;

    private Boolean isSovled;

    private Long score;

    private LocalDateTime sovledAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setBasicInfo(String title, Long tier) {
        this.title = title;
        this.tier = tier;
    }

    public void addSolvingInfo(Long score, User user) {
        this.score = score;
        this.user = user;
        user.getProblems().add(this);
        this.sovledAt = LocalDateTime.now();
        this.isSovled = true;
    }
}
