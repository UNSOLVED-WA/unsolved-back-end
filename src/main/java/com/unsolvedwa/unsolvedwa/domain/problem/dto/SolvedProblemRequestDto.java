package com.unsolvedwa.unsolvedwa.domain.problem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SolvedProblemRequestDto {
    private Long id;
    private String status;

    public SolvedProblemRequestDto(Long id, String status) {
        this.id = id;
        this.status = status;
    }
}
