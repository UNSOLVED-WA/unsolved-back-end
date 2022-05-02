package com.unsolvedwa.unsolvedwa.ranking;

import com.unsolvedwa.unsolvedwa.BaseTimeEntity;
import com.unsolvedwa.unsolvedwa.user.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Ranking extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "ranking_id")
    private Long id;

    private Long score;

    private Long month;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void increaseScore(Long score) {
        this.score += score;
    }

    public void setBasicInfo(Long month, User user) {
        this.month = month;
        this.user = user;
        user.getRankings().add(this);
    }
}
