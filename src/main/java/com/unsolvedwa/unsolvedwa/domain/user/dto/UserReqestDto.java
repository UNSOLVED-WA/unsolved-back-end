package com.unsolvedwa.unsolvedwa.domain.user.dto;

import com.unsolvedwa.unsolvedwa.domain.problem.dto.SolvedProblemRequestDto;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserReqestDto {
    private String handle;
    private List<Long> organizations;
    private List<SolvedProblemRequestDto> solved;
}
