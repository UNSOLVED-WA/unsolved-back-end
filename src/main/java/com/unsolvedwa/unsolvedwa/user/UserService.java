package com.unsolvedwa.unsolvedwa.user;

import com.unsolvedwa.unsolvedwa.user.dto.ScoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("userService")
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    User findByUserId(Long id) { return userRepository.findById(id).get(); }

    List<ScoreDto> solvingProblem(Long user_id, Long boj_id) {
        return ScoreDto.ofArray(userRepository.solvingProblem(user_id, boj_id));
    }
}
