package com.unsolvedwa.unsolvedwa.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
  public Optional<User> findByBojId(String bojId);
}
