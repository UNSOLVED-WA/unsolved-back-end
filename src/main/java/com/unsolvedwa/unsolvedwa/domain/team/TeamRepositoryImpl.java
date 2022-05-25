package com.unsolvedwa.unsolvedwa.domain.team;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TeamRepositoryImpl implements TeamRepositoryCustom {

    private JPAQueryFactory queryFactory;

}
