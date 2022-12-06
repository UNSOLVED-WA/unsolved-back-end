package com.unsolvedwa.unsolvedwa.domain.ranking;

import static com.unsolvedwa.unsolvedwa.domain.ranking.QRanking.ranking;
import static com.unsolvedwa.unsolvedwa.domain.team.QTeam.team;
import static com.unsolvedwa.unsolvedwa.domain.user.QUser.user;
import static com.unsolvedwa.unsolvedwa.domain.userteam.QUserTeam.userTeam;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.AllRankingResponseDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingHistoryResponseDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingResponseDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.QAllRankingResponseDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.QMonthRankingHistoryResponseDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.QMonthRankingResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RankingRepositoryImpl implements RankingRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public List<MonthRankingResponseDto> findMonthRanking(Long teamId, LocalDateTime startTime){
        return queryFactory
            .select(new QMonthRankingResponseDto(ranking.team.name, ranking.user.bojId, ranking.score, ranking.createAt))
            .from(ranking)
            .where(ranking.createAt.after(startTime))
            .innerJoin(ranking.team, team)
            .on(ranking.team.id.eq(teamId))
            .innerJoin(ranking.user, user)
            .orderBy(ranking.score.desc())
            .fetch();
    }

    public List<MonthRankingResponseDto> findMonthRankingByTeamAndUser(Long teamId, Long userId, LocalDateTime startTime){
        return queryFactory
            .select(new QMonthRankingResponseDto(ranking.team.name, ranking.user.bojId, ranking.score, ranking.createAt))
            .from(ranking)
            .where(ranking.createAt.after(startTime))
            .innerJoin(ranking.team, team)
            .on(ranking.team.id.eq(teamId))
            .innerJoin(ranking.user, user)
            .on(ranking.user.id.eq(userId))
            .fetch();
    }

    public List<MonthRankingHistoryResponseDto> findMonthRankingHistoryByTeamAndUser(Long teamId, Long userId){
        return queryFactory
            .select(new QMonthRankingHistoryResponseDto(ranking.team.name, ranking.user.bojId, ranking.score, ranking.monthRanking, ranking.createAt))
            .from(ranking)
            .innerJoin(ranking.team, team)
            .on(ranking.team.id.eq(teamId))
            .innerJoin(ranking.user, user)
            .on(ranking.user.id.eq(userId))
            .orderBy(ranking.createAt.desc())
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
