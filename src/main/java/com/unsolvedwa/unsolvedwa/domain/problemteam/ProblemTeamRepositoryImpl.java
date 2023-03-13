package com.unsolvedwa.unsolvedwa.domain.problemteam;

import static com.unsolvedwa.unsolvedwa.domain.problem.QProblem.problem;
import static com.unsolvedwa.unsolvedwa.domain.problemteam.QProblemTeam.problemTeam;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unsolvedwa.unsolvedwa.domain.problem.dto.ProblemResponseDto;
import com.unsolvedwa.unsolvedwa.domain.problem.dto.QProblemResponseDto;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ProblemTeamRepositoryImpl implements ProblemTeamRepositoryCustom {

	private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ProblemResponseDto> findUnsolvedRandomProblems(String teamName, Long tier) {

    	Random random = new Random(System.currentTimeMillis());
    	List<Long> count;
    	int randomNum;
    	Optional<ProblemResponseDto> result = Optional.empty();

    	count = queryFactory
    		        .select(problem.id.count())
    		        .from(problem)
    		        .leftJoin(problem.problemTeams, problemTeam)
    		        .on(problemTeam.team.name.eq(teamName))
    		        .where(problemTeam.id.isNull().and(problem.tier.eq(tier)))
    		        .fetch();

    	if (count.get(0) > 0) {
      	randomNum = random.nextInt(count.get(0).intValue()) + 1; 	// 0 <= value < max
      	result = Optional.of(queryFactory
    				.select(new QProblemResponseDto(problem.id, problem.problemNumber, problem.title, problem.tier))
    			    .from(problem)
    			    .leftJoin(problem.problemTeams, problemTeam)
    			    .on(problemTeam.team.name.eq(teamName))
    			    .where(problemTeam.id.isNull().and(problem.tier.eq(tier)))
    			    .limit(1)
    			    .offset(randomNum)
    			    .fetch()
    			    .get(0));
    	}
      	return result;
    }

	@Override
	public Optional<ProblemResponseDto> findUnsolvedRandomProblem(String teamName) {

		Random random = new Random(System.currentTimeMillis());
		List<Long> count;
		int randomNum;
		Optional<ProblemResponseDto> result = Optional.empty();

		count = queryFactory
			.select(problem.id.count())
			.from(problem)
			.leftJoin(problem.problemTeams, problemTeam)
			.on(problemTeam.team.name.eq(teamName))
			.where(problemTeam.id.isNull())
			.fetch();

		if (count.get(0) > 0) {
			randomNum = random.nextInt(count.get(0).intValue()) + 1; 	// 0 <= value < max
			result = Optional.of(queryFactory
				.select(new QProblemResponseDto(problem.id, problem.problemNumber, problem.title, problem.tier))
				.from(problem)
				.leftJoin(problem.problemTeams, problemTeam)
				.on(problemTeam.team.name.eq(teamName))
				.where(problemTeam.id.isNull())
				.limit(1)
				.offset(randomNum)
				.fetch()
				.get(0));
		}
		return result;
	}

	@Override
	public List<Long> findAllProblemNumberByTeamOrderById(Long teamId) {
		return queryFactory.select(problemTeam.problem.problemNumber)
			.from(problemTeam)
			.where(problemTeam.team.id.eq(teamId))
			.orderBy(problemTeam.problem.id.asc())
			.fetch();
	}

	public List<Long> findAllIdByTeamAndUser(Long teamId, Long userId) {
		return queryFactory.select(problemTeam.id)
			.from(problemTeam)
			.where(problemTeam.team.id.eq(teamId).and(problemTeam.user.id.eq(userId)))
			.orderBy(problemTeam.problem.id.asc())
			.fetch();
	}
}
