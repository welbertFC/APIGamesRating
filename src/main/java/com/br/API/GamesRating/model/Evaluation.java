package com.br.API.GamesRating.model;

import com.br.API.GamesRating.dto.NewEvaluationDTO;
import com.br.API.GamesRating.dto.UpdateEvaluationDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Evaluation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "EVALUATION", nullable = false, length = 1000)
    private String review;

    private LocalDateTime dateCreated;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "userClient_id")
    private UserClient userClient;

    @JsonIgnore
    @OneToMany(mappedBy = "evaluation")
    private List<Likedit> likedits = new ArrayList<>();

    public Evaluation(NewEvaluationDTO evaluationDTO, UserClient userClient, Game game) {
        this.review = evaluationDTO.getReview();
        this.dateCreated = LocalDateTime.now();
        this.game = game;
        this.userClient = userClient;
    }

    public Evaluation(Integer id, Evaluation evaluation, UpdateEvaluationDTO evaluationDTO) {
        this.id = id;
        this.review = evaluationDTO.getReview();
        this.dateCreated = evaluation.getDateCreated();
        this.game = evaluation.getGame();
        this.userClient = evaluation.getUserClient();
    }
}
