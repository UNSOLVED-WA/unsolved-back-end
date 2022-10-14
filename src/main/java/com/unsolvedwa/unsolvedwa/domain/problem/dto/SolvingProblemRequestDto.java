package com.unsolvedwa.unsolvedwa.domain.problem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SolvingProblemRequestDto {
  Long userId;
  Long problemNumber;

  public SolvingProblemRequestDto(Long userId, Long problemNumber){
    this.userId = userId;
    this.problemNumber = problemNumber;
  }
}
