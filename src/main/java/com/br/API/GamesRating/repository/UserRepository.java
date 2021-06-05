package com.br.API.GamesRating.repository;

import com.br.API.GamesRating.model.UserClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserClient, Integer> {

  UserClient findByNickName(String nickName);

  @Transactional(readOnly = true)
  UserClient findByEmail(String email);
}
