package com.br.API.GamesRating.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class NewUserDTO {

  @NotBlank(message = "Campo nome não pode ser vazio")
  @NotNull(message = "Campo nome não pode ser nulo")
  private String name;

  @NotBlank(message = "Campo nickName não pode ser vazio")
  @NotNull(message = "Campo nickName não pode ser nulo")
  private String nickName;

  @Email(message = "Formato de email invalido")
  @NotBlank(message = "Campo email não pode ser vazio")
  @NotNull(message = "Campo email não pode ser nulo")
  private String email;

  @NotBlank(message = "Campo senha é obrigatorio")
  @NotNull(message = "Campo senha não pode ser nulo")
  private String password;

  @NotNull(message = "Campo data de nascimento não pode ser nulo")
  private LocalDate birthDate;

  private String urlImage;
}
