package com.unsolvedwa.unsolvedwa.problem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

@Service("problemService")
public class ProblemService {
	@Autowired
	ProblemRepository problemRepository;

	Problem findByProblemId(String id) {
		return problemRepository.findById(id).get();
	}
}
