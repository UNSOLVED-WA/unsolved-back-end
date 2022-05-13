package com.unsolvedwa.unsolvedwa.group;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("groupService")
@Transactional
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    @Transactional(readOnly = true)
    Group findByGroupId(Long id) {
        return groupRepository.findById(id).get();
    }
}
