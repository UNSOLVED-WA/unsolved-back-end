package com.unsolvedwa.unsolvedwa.domain.ranking;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.AllRankingResponseDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingTop10ResponseDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.QAllRankingResponseDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.QMonthRankingTop10ResponseDto;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.unsolvedwa.unsolvedwa.domain.ranking.QRanking.ranking;
import static com.unsolvedwa.unsolvedwa.domain.team.QTeam.team;
import static com.unsolvedwa.unsolvedwa.domain.user.QUser.user;
import static com.unsolvedwa.unsolvedwa.domain.userteam.QUserTeam.userTeam;

@RequiredArgsConstructor
public class RankingRepositoryImpl implements RankingRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public List<MonthRankingTop10ResponseDto> findMonthRankingTop10(Long teamId, LocalDateTime startTime){
        return queryFactory
            .select(new QMonthRankingTop10ResponseDto(ranking.team.name, ranking.user.bojId, ranking.score))
            .from(ranking)
            .where(ranking.createAt.after(startTime))
            .innerJoin(ranking.team, team)
            .on(ranking.team.id.eq(teamId))
            .innerJoin(ranking.user, user)
            .orderBy(ranking.score.desc())
            .limit(10L)
            .fetch();
    }

    public List<AllRankingResponseDto> AllRanking(Long teamId){
        return queryFactory
                .select(new QAllRankingResponseDto(userTeam.team.name, userTeam.user.bojId, userTeam.score))
                .from(userTeam)
                .where(userTeam.team.id.eq(teamId))
                .innerJoin(userTeam.team, team)
                .innerJoin(userTeam.user, user)
                .orderBy(userTeam.score.desc())
                .fetch();
    }
}
