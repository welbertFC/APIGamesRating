package com.br.API.GamesRating.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class FeedDTO {

    private String nameUser;
    private String evaluationUser;
    private String titleGame;
    private String urlImage;
    private LocalDate dateEvaluationCreate;
    private Integer sumLike;
    private Integer sumDislike;

}
