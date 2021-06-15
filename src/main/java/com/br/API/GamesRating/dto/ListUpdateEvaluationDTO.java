package com.br.API.GamesRating.dto;

import com.br.API.GamesRating.model.Evaluation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListUpdateEvaluationDTO {

  private Integer id;

  private String review;

  private LocalDateTime dateCreated;

  private Integer game;

  private Integer user;

  public ListUpdateEvaluationDTO(Evaluation newEvaluation) {
    this.id = newEvaluation.getId();
    this.review = newEvaluation.getReview();
    this.dateCreated = newEvaluation.getDateCreated();
    this.game = newEvaluation.getGame().getId();
    this.user = newEvaluation.getUserClient().getId();
  }
}
