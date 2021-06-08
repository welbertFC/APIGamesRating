package com.br.API.GamesRating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckUserEmail {

    @Email(message = "Formato de email invalido")
    @NotBlank(message = "Campo email não pode ser vazio")
    @NotNull(message = "Campo email não pode ser nulo")
    private String email;
}
