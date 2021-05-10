package com.br.API.GamesRating.repository;

import com.br.API.GamesRating.model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {

    List<Evaluation> findByUser_Id(Integer id);

    List<Evaluation> findByGame_Id(Integer id);
}
