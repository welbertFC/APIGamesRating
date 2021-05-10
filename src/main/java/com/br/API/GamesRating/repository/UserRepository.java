package com.br.API.GamesRating.repository;

import com.br.API.GamesRating.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByNickName(String nickName);

    User findByEmail(String email);

}
