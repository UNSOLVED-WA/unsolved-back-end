package com.unsolvedwa.unsolvedwa.user;

import com.unsolvedwa.unsolvedwa.user.dto.ScoreDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "UserController")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(description = "유저조회")
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUser(@Parameter @PathVariable Long id) {
        return ResponseEntity.ok(userService.findByUserId(id));
    }

    @Operation(description = "특정 문제 품")
    @PostMapping(value = "/solving")
    public ResponseEntity<List<ScoreDto>> postSolving(@Parameter Long user_id, @Parameter Long boj_id) {
        return ResponseEntity.ok(userService.solvingProblem(user_id, boj_id));
    }
}
