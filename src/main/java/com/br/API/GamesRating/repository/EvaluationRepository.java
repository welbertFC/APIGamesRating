package com.br.API.GamesRating.repository;

import com.br.API.GamesRating.model.Evaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {

  Page<Evaluation> findByUserClient_Id(Integer id, Pageable pageable);

  Page<Evaluation> findByGame_Id(Integer id, Pageable pageable);

  Integer countEvaluationByUserClientId(Integer id);
}
