package com.unsolvedwa.unsolvedwa.domain.user_team;

import com.unsolvedwa.unsolvedwa.domain.BaseTimeEntity;
import com.unsolvedwa.unsolvedwa.domain.team.Team;
import com.unsolvedwa.unsolvedwa.domain.user.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class UserTeam extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "user_team_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long score;
}
