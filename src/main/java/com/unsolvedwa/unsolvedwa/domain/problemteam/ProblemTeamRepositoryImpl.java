package com.unsolvedwa.unsolvedwa.domain.problemteam;

import java.util.List;
import java.util.ArrayList;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import com.unsolvedwa.unsolvedwa.domain.problem.Problem;
import com.unsolvedwa.unsolvedwa.domain.problem.dto.UnsolvedDto;
import static com.unsolvedwa.unsolvedwa.domain.problem.QProblem.problem;
import static com.unsolvedwa.unsolvedwa.domain.problemteam.QProblem.problemteam;

@RequiredArgsConstructor
public class ProblemTeamRepositoryImpl implements ProblemTeamRepositoryCustom {

  private JPAQueryFactory queryFactory;

  @Override
  public ProblemTeam[] solvingProblem(Long user_id, Long boj_id) {
    //TODO: entityManager 이용하여 구현
    return new ProblemTeam[10];
  }
  
  @Override
  public List<Problem> findUnsolvedProblem(Long tier, Long team_id) {
	/*
	 * 1. 해당 그룹에서 풀지않은 문제 개수 조회
	 * 2. max(개수)에서 랜덤수 추출
	 * 3. 랜덤수의 row에 해당하는 문제 조회
	 */
	
	// [Temp] 확인용
	List<Problem> testList = new ArrayList<>();
	Long t = (long) 1;
	Problem dto = new Problem(t, "testTitle", t);
	testList.add(dto);

	
	/*
	 queryFactory
	 	.select(new UnsolvedDto())
	 	.from(problem)
	 	.leftjoin(problemteam)
	 	.on(problem.id.eq(problemteam.problem_id))
	 	.where(!problemteam.problem_team_id)
	*/
	return testList;
  }
}
