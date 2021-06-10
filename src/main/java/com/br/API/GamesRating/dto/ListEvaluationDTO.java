package com.br.API.GamesRating.dto;

import com.br.API.GamesRating.model.Evaluation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ListEvaluationDTO {

  private Integer id;

  private String review;

  private LocalDateTime dateCreated;

  private Integer game;

  private Integer user;

  private Integer like;

  private Integer dislike;

  public ListEvaluationDTO(Evaluation evaluation, Integer likedit, Integer dislike) {
    this.id = evaluation.getId();
    this.review = evaluation.getReview();
    this.dateCreated = evaluation.getDateCreated();
    this.game = evaluation.getGame().getId();
    this.user = evaluation.getUserClient().getId();
    this.like = likedit;
    this.dislike = dislike;
  }

  public ListEvaluationDTO(Evaluation evaluation) {
    this.id = evaluation.getId();
    this.review = evaluation.getReview();
    this.dateCreated = evaluation.getDateCreated();
    this.game = evaluation.getGame().getId();
    this.user = evaluation.getUserClient().getId();
  }
}
