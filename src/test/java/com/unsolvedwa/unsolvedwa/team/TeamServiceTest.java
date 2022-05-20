package com.unsolvedwa.unsolvedwa.team;

import com.unsolvedwa.unsolvedwa.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class TeamServiceTest {

    @Autowired TeamService teamService;
    @Autowired TeamRepository teamRepository;

    @Test
    public void 팀_랭킹_조회() throws Exception {
        //given
        for (int i = 1; i <= 20; i++)
        {
            User user = new User();
            user.setBasicInfoUser("user"+i);
            user.
        }


        // when
        // then
    }
}
