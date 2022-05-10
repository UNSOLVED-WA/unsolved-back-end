package com.unsolvedwa.unsolvedwa.group;

import com.unsolvedwa.unsolvedwa.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Group extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "group_id")
    private Long id;

    private String name;
}
