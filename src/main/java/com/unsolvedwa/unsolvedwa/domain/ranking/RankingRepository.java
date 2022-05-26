package com.unsolvedwa.unsolvedwa.domain.ranking;

import com.unsolvedwa.unsolvedwa.domain.team.Team;
import com.unsolvedwa.unsolvedwa.domain.user.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
  public List<Ranking> findAllByTeamAndUser(Team team, User user);
  public List<Ranking> findTop10ByTeamAndCreateAtAfterOrderByScoreDesc(Team team, LocalDateTime createAt);
}
