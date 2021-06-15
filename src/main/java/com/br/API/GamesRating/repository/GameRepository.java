package com.br.API.GamesRating.repository;

import com.br.API.GamesRating.model.Game;
import com.br.API.GamesRating.repository.criteria.GameRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer>, GameRepositoryCustom {

  Page<Game> findAllByActiveTrue(Pageable pageable);
}
