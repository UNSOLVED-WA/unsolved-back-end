package com.unsolvedwa.unsolvedwa.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unsolvedwa.unsolvedwa.team.ProblemTeam;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private JPAQueryFactory queryFactory;

    @Override
    public ProblemTeam[] solvingProblem(Long user_id, Long boj_id) {
        //TODO: entityManager 이용하여 구현
        return new ProblemTeam[10];
    }
}
