package com.unsolvedwa.unsolvedwa.problem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("problemService")
@Transactional
@RequiredArgsConstructor
public class ProblemService {

	private final ProblemRepository problemRepository;

	@Transactional(readOnly = true)
	Problem findByProblemId(Long id) {
		return problemRepository.findById(id).get();
	}
}
