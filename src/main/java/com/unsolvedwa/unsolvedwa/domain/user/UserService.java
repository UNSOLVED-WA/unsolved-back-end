package com.unsolvedwa.unsolvedwa.domain.user;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unsolvedwa.unsolvedwa.domain.user.dto.UserResDTO;

@Service("userService")
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public UserResDTO findByBojId(String bojId) throws NotFoundException {
    Optional<User> opsUser = userRepository.findByBojId(bojId);
    UserResDTO dto = new UserResDTO();
    if (opsUser.isEmpty())
    	throw new NotFoundException();
    dto.setId(opsUser.get().getId());
    dto.setBojId(opsUser.get().getBojId());
    dto.setSolvingCount(opsUser.get().getSolvingCount());
    return dto;
  }
}
