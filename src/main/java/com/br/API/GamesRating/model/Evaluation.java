package com.br.API.GamesRating.model;

import com.br.API.GamesRating.dto.NewEvaluationDTO;
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

    @Column(name = "EVALUATION", nullable = false, length = 900)
    private String review;

    private LocalDateTime dateCreated;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "evaluation")
    private List<Likedit> likedits = new ArrayList<>();

    public Evaluation(NewEvaluationDTO evaluationDTO, User user, Game game) {
        this.review = evaluationDTO.getReview();
        this.dateCreated = LocalDateTime.now();
        this.game = game;
        this.user = user;
    }

}
