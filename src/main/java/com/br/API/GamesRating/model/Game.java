package com.br.API.GamesRating.model;

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
    private String description;
    private String producer;
    private String platforms;
    private String urlImage;

    @OneToMany(mappedBy = "game")
    private List<Note> notes = new ArrayList<>();

    @OneToMany(mappedBy = "game")
    private List<Evaluation> evaluations = new ArrayList<>();


}
