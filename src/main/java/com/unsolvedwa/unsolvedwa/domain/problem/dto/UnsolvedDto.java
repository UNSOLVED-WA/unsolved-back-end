package com.unsolvedwa.unsolvedwa.domain.problem.dto;

import lombok.Data;
import com.querydsl.core.annotations.QueryProjection;

@Data
public class UnsolvedDto {
	  private Long id;
	  private Long problemNumber;
	  private String title;
	  private Long tier;
	  public UnsolvedDto() {
		  
	  }
	  @QueryProjection
	  public UnsolvedDto(Long id, Long problemNumber, String title, Long tier) {
		  this.id = id;
		  this.problemNumber = problemNumber;
		  this.title = title;
		  this.tier = tier;
	  }
}