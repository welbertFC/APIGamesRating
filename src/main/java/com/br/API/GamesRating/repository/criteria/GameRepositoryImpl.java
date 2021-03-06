package com.br.API.GamesRating.repository.criteria;

import com.br.API.GamesRating.filter.FilterGame;
import com.br.API.GamesRating.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

@Repository
public class GameRepositoryImpl implements GameRepositoryCustom {

  @Autowired private EntityManager entityManager;

  @Override
  public Page<Game> searchByFilter(FilterGame filterGame, Pageable pageable) {
    var criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Game> criteriaQuery = criteriaBuilder.createQuery(Game.class);

    var root = criteriaQuery.from(Game.class);

    var predicates = new ArrayList<Predicate>();

    if (filterGame.getTitle() != null) {
      predicates.add(
          criteriaBuilder.like(
              criteriaBuilder.upper(root.get("title")),
              "%" + filterGame.getTitle().toUpperCase() + "%"));
    }
    if (filterGame.getDescription() != null) {
      predicates.add(
          criteriaBuilder.like(
              criteriaBuilder.upper(root.get("description")),
              "%" + filterGame.getDescription().toUpperCase() + "%"));
    }
    if (filterGame.getProducer() != null) {
      predicates.add(
          criteriaBuilder.like(
              criteriaBuilder.upper(root.get("producer")),
              "%" + filterGame.getProducer().toUpperCase() + "%"));
    }
    if (filterGame.getPlatforms() != null) {
      predicates.add(
          criteriaBuilder.like(
              criteriaBuilder.upper(root.get("platforms")),
              "%" + filterGame.getPlatforms().toUpperCase() + "%"));
    }

    predicates.add(criteriaBuilder.equal(root.get("active"), true));

    criteriaQuery.where(predicates.toArray(new Predicate[0]));
    criteriaQuery.orderBy(criteriaBuilder.asc(root.get("title")));
    var gameslist = entityManager.createQuery(criteriaQuery).getResultList();

    return new PageImpl<>(gameslist);
  }
}
