package com.unsolvedwa.unsolvedwa.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.unsolvedwa.unsolvedwa.domain.problem.ProblemService;
import com.unsolvedwa.unsolvedwa.domain.team.Team;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RankingController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ProblemControllerTest {
  @Autowired
  MockMvc mockMvc;

  @MockBean
  ProblemService problemService;

  Long teamId;
  Long tier;
  Team team;

  @BeforeEach
  void beforeEach() throws Exception {
    teamId = 1L;
    team = new Team("teamName");
  }

  @Nested
  class GetUnsolvedTier{
    @Test
    void Sucess() throws Exception {
      //given
      List<UnsolvedProblemResponseDto> unsolvedProblemResponseDtoList = new ArrayList<>();

      //when
      given(problemService.findUnsolvedProblemByTeamAndTier(any())).willThrow(unsolvedProblemResponseDtoList);

      // then
      mockMvc.perform(get("/problems/unsolved/" + teamId + "/" + tier))
          .andExpect(status().isOk());
    }

    @Test
    void NoTeam() throws Exception {
      //given

      //when
      given(problemService.findUnsolvedProblemByTeamAndTier(any())).willThrow(new NotFoundException());

      // then
      mockMvc.perform(get("/problems/unsolved/" + teamId + "/" + tier))
          .andExpect(status().isNotFound());
    }

    @Test
    void InvalidTier() throws Exception {
      //given

      //when
      given(problemService.findUnsolvedProblemByTeamAndTier(any())).willThrow(new NotFoundException());

      // then
      mockMvc.perform(get("/problems/unsolved/" + teamId + "/" + tier))
          .andExpect(status().isNotFound());
    }

  }
}
