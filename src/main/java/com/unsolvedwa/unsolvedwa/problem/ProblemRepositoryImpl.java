package com.unsolvedwa.unsolvedwa.problem;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProblemRepositoryImpl implements ProblemRepositoryCustom{

    private final JPAQueryFactory queryFactory;

}
