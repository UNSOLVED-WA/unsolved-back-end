package com.unsolvedwa.unsolvedwa.domain.team;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

@Service("TeamService")
@Transactional
@RequiredArgsConstructor
public class TeamService {

  private final TeamRepository teamRepository;

  @Transactional(readOnly = true)
  public Team findByTeamId(Long id) {
    return teamRepository.findById(id).get();
  }

  public Team findByTeamName(String teamName) {
    Optional<Team> optionalTeam = teamRepository.findByName(teamName);
    if (optionalTeam.isEmpty()) {
      throw new NotFoundException("해당 이름의 팀이 존재하지 않습니다.");
    }
    return optionalTeam.get();
  }
}
