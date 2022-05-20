package com.unsolvedwa.unsolvedwa.problem;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.unsolvedwa.unsolvedwa.problem.QProblem.problem;

@RequiredArgsConstructor
public class ProblemRepositoryImpl implements ProblemRepositoryCustom{

    private final JPAQueryFactory queryFactory;

}
