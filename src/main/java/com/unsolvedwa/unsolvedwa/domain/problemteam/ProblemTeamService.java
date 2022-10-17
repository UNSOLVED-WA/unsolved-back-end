package com.unsolvedwa.unsolvedwa.domain.problemteam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("problemTeamService")
@Transactional
@RequiredArgsConstructor
public class ProblemTeamService {

  private final ProblemTeamRepository problemTeamRepository;
}
