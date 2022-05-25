package com.unsolvedwa.unsolvedwa.domain.ranking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("RankingService")
@Transactional
@RequiredArgsConstructor
public class RankingService {
    private final RankingRepository rankingRepository;

    public List<Ranking> findById(Long id) {
        //TODO: repository 구현하여 작성
        return new ArrayList<Ranking>();
    }
}
