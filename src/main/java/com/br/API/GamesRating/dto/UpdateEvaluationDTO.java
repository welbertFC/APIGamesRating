package com.br.API.GamesRating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEvaluationDTO {

    @NotNull(message = "A resenha não pode ser nulo")
    @Length(min = 5, max = 1000, message = "A resenha deve ter entre 5 e 1000 caracteres")
    private String review;
}
