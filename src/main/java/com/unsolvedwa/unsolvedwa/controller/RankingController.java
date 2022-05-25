package com.unsolvedwa.unsolvedwa.controller;

import com.unsolvedwa.unsolvedwa.domain.ranking.Ranking;
import com.unsolvedwa.unsolvedwa.domain.ranking.RankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "RankingCcontroller")
@RequestMapping("/rankings")
@RequiredArgsConstructor
public class RankingController {
    private final RankingService rankingService;

    @Operation(description = "전체기간랭킹")
    @GetMapping(value = "/{team_id}")
    public ResponseEntity<List<Ranking>> getRanking(@Parameter @PathVariable Long team_id) {
        //TODO: service 구현하여 작성
        return ResponseEntity.ok(rankingService.findById(team_id));
    }

    @Operation(description = "월간랭킹")
    @GetMapping(value = "/month/{team_id}")
    public ResponseEntity<List<Ranking>> getRankingMonth(@Parameter @PathVariable Long team_id) {
        //TODO: service 구현하여 작성
        return ResponseEntity.ok(rankingService.findById(team_id));
    }
}
