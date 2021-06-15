package com.br.API.GamesRating.repository.criteria;

import com.br.API.GamesRating.filter.FilterGame;
import com.br.API.GamesRating.model.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GameRepositoryCustom {

  Page<Game> searchByFilter(FilterGame filterGame, Pageable pageable);
}
