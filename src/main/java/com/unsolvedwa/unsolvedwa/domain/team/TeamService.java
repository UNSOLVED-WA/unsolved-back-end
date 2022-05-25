package com.unsolvedwa.unsolvedwa.domain.team;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("TeamService")
@Transactional
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    @Transactional(readOnly = true)
    public Team findByTeamId(Long id) {
        return teamRepository.findById(id).get();
    }
}
