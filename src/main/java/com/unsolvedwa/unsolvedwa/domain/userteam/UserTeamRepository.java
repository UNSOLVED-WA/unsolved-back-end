package com.unsolvedwa.unsolvedwa.domain.userteam;

import com.unsolvedwa.unsolvedwa.domain.team.Team;
import com.unsolvedwa.unsolvedwa.domain.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {
  public Optional<UserTeam> findByTeamAndUser(Team team, User user);
  public List<UserTeam> findByTeam(Team team);
  public List<UserTeam> findAllByUser(User user);
  public List<UserTeam> findTop10ByTeamOrderByScoreDesc(Team team);
}
