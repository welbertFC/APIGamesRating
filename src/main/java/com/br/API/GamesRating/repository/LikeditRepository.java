package com.br.API.GamesRating.repository;

import com.br.API.GamesRating.model.Likedit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeditRepository extends JpaRepository<Likedit, Integer> {
}
