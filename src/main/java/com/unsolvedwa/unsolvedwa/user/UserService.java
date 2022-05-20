package com.unsolvedwa.unsolvedwa.user;

import com.unsolvedwa.unsolvedwa.user.dto.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("userService")
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    User findByUserId(Long id) { return userRepository.findById(id).get(); }

    Score[] solvingProblem(Long user_id, Long boj_id) {
        return userRepository.solvingProblem(user_id, boj_id);
    }
}
