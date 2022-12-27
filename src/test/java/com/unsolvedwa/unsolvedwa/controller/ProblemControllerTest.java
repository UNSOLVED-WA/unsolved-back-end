package com.unsolvedwa.unsolvedwa.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.unsolvedwa.unsolvedwa.domain.problem.ProblemService;
import com.unsolvedwa.unsolvedwa.domain.problem.dto.ProblemResponseDto;
import com.unsolvedwa.unsolvedwa.domain.problemteam.ProblemTeamService;
import com.unsolvedwa.unsolvedwa.domain.problem.dto.SolvingProblemRequestDto;
import com.unsolvedwa.unsolvedwa.domain.problem.dto.SolvingProblemResponseDto;
import com.unsolvedwa.unsolvedwa.domain.team.Team;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProblemController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ProblemControllerTest {
  @Autowired
  MockMvc mockMvc;

  @MockBean
  ProblemService problemService;

  @MockBean
  ProblemTeamService problemTeamService;

  Long teamId;
  Long tier;
  Team team;

  @BeforeEach
  void beforeEach() throws Exception {
    teamId = 1L;
    tier = 1L;
    team = new Team("teamName");
  }

  @Nested
  class SolvingProblem{
    @Test
    void Success() throws Exception {
      //given
      //when
      String bojId = "user1";
      Long problemId = 1L;
      Long problemNumber = 1L;
      String problemTitle = "problem";
      Long score = 1L;

      List<SolvingProblemResponseDto> solvingProblemResponseDtoList = new ArrayList<>();

      for (int i = 0; i < 3; i++) {
        String teamName = "team" + (i + 1);
        SolvingProblemResponseDto solvingProblemResponseDto = new SolvingProblemResponseDto(
            problemId, problemTitle, tier, score, teamName);
        solvingProblemResponseDtoList.add(solvingProblemResponseDto);
      }

      given(problemService.solveProblem(bojId, problemNumber))
          .willReturn(solvingProblemResponseDtoList);

      SolvingProblemRequestDto solvingProblemRequestDto = new SolvingProblemRequestDto(bojId, problemNumber);
      Gson gson = new Gson();
      String content = gson.toJson(solvingProblemRequestDto);

      // then
      mockMvc.perform(post("/problems/solving")
              .contentType(MediaType.APPLICATION_JSON)
              .content(content))
          .andExpect(status().isOk());
    }

    @Test
    void NoUser() throws Exception {
      //given
      //when
      String bojId = "user1";
      Long problemNumber = 1L;

      given(problemService.solveProblem(bojId, problemNumber))
          .willThrow(new NotFoundException());

      SolvingProblemRequestDto solvingProblemRequestDto = new SolvingProblemRequestDto(bojId, problemNumber);
      Gson gson = new Gson();
      String content = gson.toJson(solvingProblemRequestDto);

      // then
      mockMvc.perform(post("/problems/solving")
              .contentType(MediaType.APPLICATION_JSON)
              .content(content))
          .andExpect(status().isNotFound());
    }

    @Test
    void NoProblem() throws Exception {
      //given
      //when
      String bojId = "user1";
      Long problemNumber = 1L;

      given(problemService.solveProblem(bojId, problemNumber))
          .willThrow(new NotFoundException());

      SolvingProblemRequestDto solvingProblemRequestDto = new SolvingProblemRequestDto(bojId, problemNumber);
      Gson gson = new Gson();
      String content = gson.toJson(solvingProblemRequestDto);

      // then
      mockMvc.perform(post("/problems/solving")
              .contentType(MediaType.APPLICATION_JSON)
              .content(content))
          .andExpect(status().isNotFound());
    }

  }

  @Nested
  class GetUnsolvedTier{
    @Test
    void Success() throws Exception {
      //given
      List<ProblemResponseDto> problemResponseDtoList = new ArrayList<>();

      //when
      given(problemService.findUnsolvedProblemsByTeamAndTier(any(), any()))
          .willReturn(problemResponseDtoList);

      // then
      mockMvc.perform(get("/problems/unsolved/" + teamId + "/" + tier))
          .andExpect(status().isOk());
    }

    @Test
    void NoTeam() throws Exception {
      //given

      //when
      given(problemService.findUnsolvedProblemsByTeamAndTier(any(), any()))
          .willThrow(new NotFoundException());

      // then
      mockMvc.perform(get("/problems/unsolved/" + teamId + "/" + tier))
          .andExpect(status().isNotFound());
    }

    @Test
    void InvalidTier() throws Exception {
      //given

      //when
      given(problemService.findUnsolvedProblemsByTeamAndTier(any(), any()))
          .willThrow(new NotFoundException());

      // then
      mockMvc.perform(get("/problems/unsolved/" + teamId + "/" + tier))
          .andExpect(status().isNotFound());
    }

  }
}
