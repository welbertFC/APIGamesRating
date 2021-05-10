package com.br.API.GamesRating.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class NewEvaluationDTO {

    @NotNull(message = "A resenha não pode ser nulo")
    @Length(min = 5, max = 300, message = "A resenha deve ter entre 5 e 300 caracteres")
    private String review;

    @NotNull(message = "O jogo não pode ser nulo")
    private Integer game;

    @NotNull(message = "o Usuario não pode ser nulo")
    private Integer user;
}
