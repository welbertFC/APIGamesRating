package com.br.API.GamesRating.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
public class UpdateUserDTO {

  @NotBlank(message = "Campo nome não pode ser vazio")
  @NotNull(message = "Campo nome não pode ser nulo")
  private String name;

  @NotBlank(message = "Campo nickName não pode ser vazio")
  @NotNull(message = "Campo nickName não pode ser nulo")
  private String nickName;

  @Length(min = 6, max = 16, message = "Tamanho invalido")
  @NotBlank(message = "Campo senha é obrigatorio")
  @NotNull(message = "Campo senha não pode ser nulo")
  private String password;

  @NotNull(message = "Campo data de nascimento não pode ser nulo")
  private LocalDate birthDate;

  private Set<Integer> profile;
}
