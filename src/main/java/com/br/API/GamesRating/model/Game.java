package com.br.API.GamesRating.model;

import com.br.API.GamesRating.dto.NewGameDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Game implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String title;

  @Column(name = "DESCRIPTION", nullable = false, length = 1000)
  private String description;

  private String producer;
  private String platforms;
  private String urlImage;
  private Boolean active;

  @JsonIgnore
  @OneToMany(mappedBy = "game")
  private List<Note> notes = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "game")
  private List<Evaluation> evaluations = new ArrayList<>();

  public Game(NewGameDTO newGameDTO, URI image) {
    this.title = newGameDTO.getTitle();
    this.description = newGameDTO.getDescription();
    this.producer = newGameDTO.getProducer();
    this.platforms = newGameDTO.getPlatforms();
    this.urlImage = image.toString();
    this.active = (newGameDTO.getActive() == null) ? true : newGameDTO.getActive();
  }

  public Game(NewGameDTO newGameDTO, Game game) {
    this.id = game.getId();
    this.title = newGameDTO.getTitle();
    this.description = newGameDTO.getDescription();
    this.producer = newGameDTO.getProducer();
    this.platforms = newGameDTO.getPlatforms();
    this.urlImage = game.getUrlImage();
    this.active = (newGameDTO.getActive() == null) ? true : newGameDTO.getActive();
  }
}
