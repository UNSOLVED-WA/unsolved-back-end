package com.unsolvedwa.unsolvedwa.problem;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("problemService")
@RequiredArgsConstructor
public class ProblemService {

	private final ProblemRepository problemRepository;

	Problem findByProblemId(String id) {
		return problemRepository.findById(id).get();
	}
}
