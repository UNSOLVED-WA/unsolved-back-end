package com.unsolvedwa.unsolvedwa.user;

import com.unsolvedwa.unsolvedwa.BaseTimeEntity;
import com.unsolvedwa.unsolvedwa.group.ProblemGroup;
import com.unsolvedwa.unsolvedwa.group.UserGroup;
import com.unsolvedwa.unsolvedwa.problem.Problem;
import com.unsolvedwa.unsolvedwa.ranking.Ranking;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class User extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String bojId;

    private Long solvingCount;

    private Long score;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ProblemGroup> problemGroups;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Ranking> rankings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserGroup> userGroups;

    public void setBasicInfoUser(String bojId, Boolean isMember) {
        this.bojId = bojId;
    }

    public void addSolvingInfo(Long score) {
        this.score += score;
        this.solvingCount++;
    }
}
