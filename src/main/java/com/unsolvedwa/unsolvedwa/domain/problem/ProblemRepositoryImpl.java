package com.unsolvedwa.unsolvedwa.domain.problem;

import static com.unsolvedwa.unsolvedwa.domain.problem.QProblem.problem;
import static com.unsolvedwa.unsolvedwa.domain.problemteam.QProblemTeam.problemTeam;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unsolvedwa.unsolvedwa.domain.problem.dto.ProblemResponseDto;
import com.unsolvedwa.unsolvedwa.domain.problem.dto.QProblemResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProblemRepositoryImpl implements ProblemRepositoryCustom {
  private final JPAQueryFactory queryFactory;


  public List<ProblemResponseDto> findUnsolvedProblemsByTeamAndTier(Long teamId, Long tier) {
    return queryFactory
        .select(new QProblemResponseDto(problem.id, problem.title, problem.tier))
        .from(problem)
        .leftJoin(problem.problemTeams, problemTeam)
        .on(problemTeam.team.id.eq(teamId))
        .where(problemTeam.id.isNull().and(problem.tier.eq(tier)))
        .fetch();
  }

}
