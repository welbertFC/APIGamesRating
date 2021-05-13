package com.br.API.GamesRating.repository;

import com.br.API.GamesRating.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    Users findByNickName(String nickName);

    Users findByEmail(String email);
}
