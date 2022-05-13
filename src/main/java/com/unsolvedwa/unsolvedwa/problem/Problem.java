package com.unsolvedwa.unsolvedwa.problem;

import com.unsolvedwa.unsolvedwa.BaseTimeEntity;
import com.unsolvedwa.unsolvedwa.group.ProblemGroup;
import com.unsolvedwa.unsolvedwa.user.User;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Problem extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "problem_id")
    private Long id;

    private String title;

    private Long tier;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<ProblemGroup> problemGroups;

    public void setBasicInfo(String title, Long tier) {
        this.title = title;
        this.tier = tier;
    }
}
