package com.unsolvedwa.unsolvedwa.domain.user;

import com.unsolvedwa.unsolvedwa.domain.problem.Problem;
import com.unsolvedwa.unsolvedwa.domain.problem.ProblemRepository;
import com.unsolvedwa.unsolvedwa.domain.problem.dto.SolvedProblemRequestDto;
import com.unsolvedwa.unsolvedwa.domain.problemteam.ProblemTeam;
import com.unsolvedwa.unsolvedwa.domain.problemteam.ProblemTeamRepository;
import com.unsolvedwa.unsolvedwa.domain.team.Team;
import com.unsolvedwa.unsolvedwa.domain.team.TeamRepository;
import com.unsolvedwa.unsolvedwa.domain.user.dto.UserReqestDto;
import com.unsolvedwa.unsolvedwa.domain.user.dto.UserResDTO;
import com.unsolvedwa.unsolvedwa.domain.userteam.UserTeam;
import com.unsolvedwa.unsolvedwa.domain.userteam.UserTeamRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final TeamRepository teamRepository;
  private final UserTeamRepository userTeamRepository;
  private final ProblemTeamRepository problemTeamRepository;
  private final ProblemRepository problemRepository;

  @Transactional(readOnly = true)
  public UserResDTO findByBojId(String bojId) throws NotFoundException {
    Optional<User> optionalUser = userRepository.findByBojId(bojId);
    if (optionalUser.isEmpty())
    	throw new NotFoundException();
    return new UserResDTO(optionalUser.get().getId(), optionalUser.get().getBojId(), optionalUser.get().getSolvingCount());
  }

  public UserResDTO updateUserInfo(UserReqestDto userReqestDto) throws NotFoundException{
    Optional<User> optionalUser = userRepository.findByBojId(userReqestDto.getHandle());
    User user;
    List<UserTeam> userTeamList;

    if(optionalUser.isEmpty()) {
      user = createUser(userReqestDto);
      userTeamList = createUserTeam(userReqestDto.getOrganizations(), user);
    }
    else {
      user = optionalUser.get();
      userTeamList = updateUserTeamInfo(userReqestDto, user);
    }

    for (int i = 0; i < userTeamList.size(); i++) {
      updateUserProblemInfo(userReqestDto, user, userTeamList.get(i));
    }

    return new UserResDTO(user.getId(), user.getBojId(), user.getSolvingCount());
  }

  private void updateUserProblemInfo(UserReqestDto userReqestDto, User user, UserTeam userTeam) throws NotFoundException{
    Team team = userTeam.getTeam();
    List<Long> prevProblemNumberList = problemTeamRepository.findAllProblemNumberByTeamOrderById(team.getId());
    List<SolvedProblemRequestDto> solvedProblemRequestDtoList = userReqestDto.getSolved();

    int i = 0;
    int j = 0;
    while (i < prevProblemNumberList.size() && j < solvedProblemRequestDtoList.size()) {
      while (j < solvedProblemRequestDtoList.size()) {
        if (solvedProblemRequestDtoList.get(j).getStatus().equals("solved")) {
          break;
        }
        j++;
      }
      int compared = prevProblemNumberList.get(i).compareTo(solvedProblemRequestDtoList.get(j).getId());
      if (compared < 0) {
        i++;
      }
      else if (compared == 0) {
        i++;
        j++;
      }
      else {
        createProblemTeamByProblemNumber(solvedProblemRequestDtoList.get(j).getId(), team, user);
        j++;
      }
    }
    while (j < solvedProblemRequestDtoList.size()) {
      createProblemTeamByProblemNumber(solvedProblemRequestDtoList.get(j).getId(), team, user);
      j++;
    }
  }

  private ProblemTeam createProblemTeamByProblemNumber(Long problemNumber, Team team, User user) throws NotFoundException{
    Optional<Problem> optionalProblem = problemRepository.findByProblemNumber(problemNumber);
    if (optionalProblem.isEmpty()) {
      throw new NotFoundException();
    }
    ProblemTeam problemTeam = new ProblemTeam(optionalProblem.get(), team, user);
    return problemTeamRepository.save(problemTeam);
  }

  private List<UserTeam> updateUserTeamInfo(UserReqestDto userReqestDto, User user) {
    List<UserTeam> prevUserTeamList = userTeamRepository.findAllByUser(user);

    deleteNoMatchedUserTeam(userReqestDto, prevUserTeamList, user);
    return updateNoMatchedUserTeam(userReqestDto, prevUserTeamList, user);
  }

  private List<UserTeam> updateNoMatchedUserTeam(UserReqestDto userReqestDto, List<UserTeam> prevUserTeamList, User user) {
    List<Long> noMatchedTeamIdList = new ArrayList<>();

    for (int i = 0; i < userReqestDto.getOrganizations().size(); i++) {
      Long curTeamId = userReqestDto.getOrganizations().get(i);
      boolean noMatched = true;
      for (int j = 0; j < prevUserTeamList.size(); j++) {
        if (curTeamId.equals(prevUserTeamList.get(j).getTeam().getId())) {
          noMatched = false;
          break;
        }
      }
      if (noMatched) {
        noMatchedTeamIdList.add(curTeamId);
      }
    }
    return createUserTeam(noMatchedTeamIdList, user);
  }

  private void deleteNoMatchedUserTeam(UserReqestDto userReqestDto, List<UserTeam> prevUserTeamList, User user) {
    for (int i = 0; i < prevUserTeamList.size(); i++) {
      Long prevTeamId = prevUserTeamList.get(i).getTeam().getId();
      boolean noMatched = true;
      for (int j = 0; j < userReqestDto.getOrganizations().size(); j++) {
        if (prevTeamId.equals(userReqestDto.getOrganizations().get(j))) {
          noMatched = false;
          break;
        }
      }
      if (noMatched) {
        deleteProblemTeamByUser(prevUserTeamList.get(i), user);
        userTeamRepository.delete(prevUserTeamList.get(i));
      }
    }
  }

  private void deleteProblemTeamByUser(UserTeam userTeam, User user) {
    List<Long> problemTeamList = problemTeamRepository.findAllIdByTeamAndUser(userTeam.getTeam().getId(), user.getId());
    problemTeamRepository.deleteAllById(problemTeamList);
  }

  private User createUser(UserReqestDto userReqestDto){
    User user = new User(userReqestDto.getHandle());
    return userRepository.save(user);
  }

  private List<UserTeam> createUserTeam(List<Long> teamIdList, User user) {
    List<UserTeam> userTeamList = new ArrayList<>();
    for (int i = 0; i < teamIdList.size(); i++) {
      Optional<Team> optionalTeam = teamRepository.findById(teamIdList.get(i));
      if (!optionalTeam.isEmpty()) {
        UserTeam userTeam = new UserTeam(optionalTeam.get(), user);
        UserTeam savedUserTeam = userTeamRepository.save(userTeam);
        userTeamList.add(savedUserTeam);
      }
    }
    return userTeamList;
  }

}
