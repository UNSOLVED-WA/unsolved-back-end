package com.unsolvedwa.unsolvedwa.domain.ranking;

import static com.unsolvedwa.unsolvedwa.domain.ranking.QRanking.ranking;
import static com.unsolvedwa.unsolvedwa.domain.team.QTeam.team;
import static com.unsolvedwa.unsolvedwa.domain.user.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingTop10ResponseDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.QMonthRankingTop10ResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

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
            .fetch();
    }
}
