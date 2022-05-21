package com.unsolvedwa.unsolvedwa.user;

import com.unsolvedwa.unsolvedwa.BaseTimeEntity;
import com.unsolvedwa.unsolvedwa.team.ProblemTeam;
import com.unsolvedwa.unsolvedwa.team.Team;
import com.unsolvedwa.unsolvedwa.team.UserTeam;
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

    private Long solvingCount;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ProblemTeam> problemTeams;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Ranking> rankings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserTeam> userTeams;

    public void setBasicInfoUser(String bojId) {
        this.bojId = bojId;
    }
}
