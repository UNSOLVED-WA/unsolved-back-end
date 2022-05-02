package com.unsolvedwa.unsolvedwa.problem;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "ProblemController")
@RequestMapping("/problem")
public class ProblemController {
    @Operation(description = "문제조회")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Problem> getProblem(@Parameter @PathVariable String id) {
        ProblemService problemService = new ProblemService();
        return ResponseEntity.ok(problemService.findByProblemId(id));
    }
}
