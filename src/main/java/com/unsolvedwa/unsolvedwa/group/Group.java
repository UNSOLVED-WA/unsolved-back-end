package com.unsolvedwa.unsolvedwa.group;

import com.unsolvedwa.unsolvedwa.BaseTimeEntity;
import com.unsolvedwa.unsolvedwa.ranking.Ranking;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Group extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "group_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<UserGroup> userGroups;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<ProblemGroup> problemGroups;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Ranking> rankings;
}
