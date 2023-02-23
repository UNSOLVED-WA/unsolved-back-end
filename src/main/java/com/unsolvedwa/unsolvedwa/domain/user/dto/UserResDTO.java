package com.unsolvedwa.unsolvedwa.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResDTO {
	 private Long id;
	 private String bojId;
	 private Long solvingCount;

	public UserResDTO(Long id, String bojId, Long solvingCount) {
		this.id = id;
		this.bojId = bojId;
		this.solvingCount = solvingCount;
	}
}
