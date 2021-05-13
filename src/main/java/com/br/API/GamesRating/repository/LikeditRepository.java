package com.br.API.GamesRating.repository;

import com.br.API.GamesRating.model.Likedit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeditRepository extends JpaRepository<Likedit, Integer> {

    List<Likedit> findByEvaluation_Id(Integer id);

    Likedit findByUserClient_IdAndAndEvaluation_Id(Integer idUser, Integer idEvaluation);


}
