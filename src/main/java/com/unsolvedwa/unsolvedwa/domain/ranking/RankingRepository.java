package com.unsolvedwa.unsolvedwa.domain.ranking;

import com.unsolvedwa.unsolvedwa.domain.team.Team;
import com.unsolvedwa.unsolvedwa.domain.user.User;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankingRepository extends JpaRepository<Ranking, Long>, RankingRepositoryCustom {
  public Optional<Ranking> findByTeamAndUserAndCreateAtAfter(Team team, User user, LocalDateTime startTime);
}
