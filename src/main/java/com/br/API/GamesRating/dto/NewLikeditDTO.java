package com.br.API.GamesRating.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class NewLikeditDTO {

  @Range(min = 1, max = 3, message = "Só é aceito numeros de 1 a 3")
  private Integer likeDit;

  @NotNull(message = "O usuario não pode ser nulo")
  private Integer user;

  @NotNull(message = "A avaliação não pode ser nulo")
  private Integer evaluation;
}
