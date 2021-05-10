package com.br.API.GamesRating.model;

import com.br.API.GamesRating.dto.GameDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
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

    @Column(name="DESCRIPTION", nullable=false, length=900)
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

    public Game(GameDTO gameDTO) {
        this.title = gameDTO.getTitle();
        this.description = gameDTO.getDescription();
        this.producer = gameDTO.getProducer();
        this.platforms = gameDTO.getPlatforms();
        this.urlImage = gameDTO.getUrlImage();
        this.active = (gameDTO.getActive() == null ) ? true : gameDTO.getActive();
    }

    public Game(Integer id, GameDTO gameDTO) {
        this.id = id;
        this.title = gameDTO.getTitle();
        this.description = gameDTO.getDescription();
        this.producer = gameDTO.getProducer();
        this.platforms = gameDTO.getPlatforms();
        this.urlImage = gameDTO.getUrlImage();
        this.active = (gameDTO.getActive() == null ) ? true : gameDTO.getActive();
    }
}

