package com.unsolvedwa.unsolvedwa.user;

import com.unsolvedwa.unsolvedwa.user.dto.Score;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class UserCustomRepositoryImpl implements UserCustomRepository {
    EntityManager entityManager;

    @Override
    public Score[] solvingProblem(Long user_id, Long boj_id) {
        //TODO: entityManager 이용하여 구현
        return new Score[10];
    }
}
