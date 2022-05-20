package com.unsolvedwa.unsolvedwa.team;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("TeamService")
@Transactional
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    @Transactional(readOnly = true)
    Team findByTeamId(Long id) {
        return teamRepository.findById(id).get();
    }
}
