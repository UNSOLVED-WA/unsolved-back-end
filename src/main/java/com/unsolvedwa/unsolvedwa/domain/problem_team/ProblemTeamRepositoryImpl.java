package com.unsolvedwa.unsolvedwa.domain.problem_team;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProblemTeamRepositoryImpl implements ProblemTeamRepositoryCustom {

  private JPAQueryFactory queryFactory;

  @Override
  public ProblemTeam[] solvingProblem(Long user_id, Long boj_id) {
    //TODO: entityManager 이용하여 구현
    return new ProblemTeam[10];
  }
}
