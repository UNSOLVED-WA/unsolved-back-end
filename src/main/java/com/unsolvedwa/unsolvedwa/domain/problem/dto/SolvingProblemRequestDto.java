package com.unsolvedwa.unsolvedwa.domain.problem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SolvingProblemRequestDto {
  String bojId;
  Long problemNumber;

  public SolvingProblemRequestDto(String bojId, Long problemNumber){
    this.bojId = bojId;
    this.problemNumber = problemNumber;
  }
}
