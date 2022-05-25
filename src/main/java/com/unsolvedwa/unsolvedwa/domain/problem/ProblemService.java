package com.unsolvedwa.unsolvedwa.domain.problem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ProblemService")
@Transactional
@RequiredArgsConstructor
public class ProblemService {

  private final ProblemRepository problemRepository;

  @Transactional(readOnly = true)
  public Problem findByProblemId(Long id) {
    return problemRepository.findById(id).get();
  }
}
