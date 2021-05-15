package com.br.API.GamesRating.dto;

import com.br.API.GamesRating.model.Game;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListGameDTO {

  private Integer id;
  private String title;
  private String description;
  private String producer;
  private String platforms;
  private String urlImage;
  private Integer note;

  public ListGameDTO(Integer note, Game game) {
    this.id = game.getId();
    this.title = game.getTitle();
    this.description = game.getDescription();
    this.producer = game.getProducer();
    this.platforms = game.getPlatforms();
    this.urlImage = game.getUrlImage();
    this.note = note;
  }
}
