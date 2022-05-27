package com.unsolvedwa.unsolvedwa.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
  public User findByBojId(String bojId);
}
