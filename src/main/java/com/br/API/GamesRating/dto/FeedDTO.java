package com.br.API.GamesRating.dto;

import com.br.API.GamesRating.model.Evaluation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FeedDTO {

  private Integer idEvaluation;
  private Integer idGame;
  private Integer idUser;
  private String nickNameUser;
  private String evaluationUser;
  private String titleGame;
  private String urlImage;
  private String urlImageUser;

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  private LocalDateTime dateEvaluationCreate;

  private Integer like;
  private Integer dislike;

  public FeedDTO(Evaluation evaluation, Integer likedit, Integer dislike) {
    this.idUser = evaluation.getUserClient().getId();
    this.idGame = evaluation.getGame().getId();
    this.idEvaluation = evaluation.getId();
    this.nickNameUser = evaluation.getUserClient().getNickName();
    this.evaluationUser = evaluation.getReview();
    this.titleGame = evaluation.getGame().getTitle();
    this.urlImage = evaluation.getGame().getUrlImage();
    this.dateEvaluationCreate = evaluation.getDateCreated();
    this.like = likedit;
    this.dislike = dislike;
    this.urlImageUser = evaluation.getUserClient().getUrlImage();
  }
}
