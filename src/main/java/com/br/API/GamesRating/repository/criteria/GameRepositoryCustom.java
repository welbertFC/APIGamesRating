package com.br.API.GamesRating.repository.criteria;

import com.br.API.GamesRating.dto.ListGameDTO;
import com.br.API.GamesRating.filter.FilterGame;
import com.br.API.GamesRating.model.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GameRepositoryCustom {

    Page<Game> searchByFilter(FilterGame filterGame, Pageable pageable);
}
