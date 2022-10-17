package com.unsolvedwa.unsolvedwa.domain.problemteam;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProblemTeamRepositoryImpl implements ProblemTeamRepositoryCustom {
  private JPAQueryFactory queryFactory;
}
