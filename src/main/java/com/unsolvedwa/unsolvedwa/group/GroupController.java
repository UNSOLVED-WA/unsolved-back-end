package com.unsolvedwa.unsolvedwa.group;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name= "GroupController")
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @Operation(description = "그룹조회")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Group> getGroup(@Parameter @PathVariable Long id) {
        return ResponseEntity.ok(groupService.findByGroupId(id));
    }
}
