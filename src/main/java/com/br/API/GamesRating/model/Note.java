package com.br.API.GamesRating.model;

import com.br.API.GamesRating.dto.NewNoteDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class Note implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer note;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "userClient_id")
  private UserClient userClient;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "game_id")
  private Game game;

  public Note(NewNoteDTO newNoteDTO, UserClient userClient, Game game) {
    this.note = newNoteDTO.getNote();
    this.userClient = userClient;
    this.game = game;
  }
}
