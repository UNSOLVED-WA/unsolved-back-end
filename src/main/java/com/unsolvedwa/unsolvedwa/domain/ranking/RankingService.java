package com.unsolvedwa.unsolvedwa.domain.ranking;

import com.unsolvedwa.unsolvedwa.domain.ranking.dto.AllRankingResponseDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingRequestDto;
import com.unsolvedwa.unsolvedwa.domain.ranking.dto.MonthRankingTop10ResponseDto;
import com.unsolvedwa.unsolvedwa.domain.team.Team;
import com.unsolvedwa.unsolvedwa.domain.team.TeamRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("RankingService")
@Transactional
@RequiredArgsConstructor
public class RankingService {

  private final RankingRepository rankingRepository;
  private final TeamRepository teamRepository;

  private LocalDateTime getCurMonthDateTime() {
    LocalDateTime curDateTime = LocalDateTime.now();
    return LocalDateTime.of(curDateTime.getYear(), curDateTime.getMonth(), 1, 0, 0);
  }

  public List<Ranking> findById() {
    //TODO: repository 구현하여 작성
    return new ArrayList<>();
  }

  public List<MonthRankingTop10ResponseDto> findMonthRankingAtThisMonth(MonthRankingRequestDto monthRankingRequestDto) throws NotFoundException {
    Optional<Team> temp = teamRepository.findById(monthRankingRequestDto.getTeamId());
    if (temp.isEmpty()) {
      throw new NotFoundException();
    }
    LocalDateTime startTime = getCurMonthDateTime();
    return rankingRepository.findMonthRankingTop10(monthRankingRequestDto.getTeamId(), startTime);
  }

  public List<AllRankingResponseDto> AllRanking(Long teamId) throws NotFoundException {
    Optional<Team> temp = teamRepository.findById(teamId);
    if (temp.isEmpty()) {
      throw new NotFoundException();
    }
    return rankingRepository.AllRanking(teamId);
  }
}
