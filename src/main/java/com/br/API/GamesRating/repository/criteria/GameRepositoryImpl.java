package com.br.API.GamesRating.repository.criteria;

import com.br.API.GamesRating.dto.ListGameDTO;
import com.br.API.GamesRating.filter.FilterGame;
import com.br.API.GamesRating.model.Game;
import javax.persistence.criteria.CriteriaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GameRepositoryImpl implements GameRepositoryCustom{

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<Game> searchByFilter(FilterGame filterGame, Pageable pageable) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Game> criteriaQuery = criteriaBuilder.createQuery(Game.class);

        var root = criteriaQuery.from(Game.class);

        var predicates = new ArrayList<Predicate>();

        if(filterGame.getTitle() != null){
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("title")), "%" + filterGame.getTitle().toUpperCase() + "%"));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        var gameslist = entityManager.createQuery(criteriaQuery).getResultList();

        return new PageImpl<>(gameslist) ;


    }
}
