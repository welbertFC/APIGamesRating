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

    public ListEvaluationDTO(Evaluation evaluation) {
        this.id = evaluation.getId();
        this.review = evaluation.getReview();
        this.dateCreated = evaluation.getDateCreated();
        this.game = evaluation.getGame().getId();
        this.user = evaluation.getUser().getId();
    }
}
