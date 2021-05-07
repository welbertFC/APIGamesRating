package com.br.API.GamesRating.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class NewNoteDTO {

    @NotNull(message = "nota não pode ser nulo")
    @Range(min = 1, max = 5, message = "A nota deve ser entre 1 e 5")
    private Integer note;

    @NotNull(message = "nota não pode ser nulo")
    private Integer user;

    @NotNull(message = "nota não pode ser nulo")
    private Integer game;
}
