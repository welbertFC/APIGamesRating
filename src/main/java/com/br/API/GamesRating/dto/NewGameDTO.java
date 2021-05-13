package com.br.API.GamesRating.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class NewGameDTO {

    @NotBlank(message = "Campo title não pode ser vazio")
    @NotNull(message = "Campo title não pode ser nulo")
    private String title;

    @NotBlank(message = "Campo description não pode ser vazio")
    @NotNull(message = "Campo description não pode ser nulo")
    @Length(min = 10, max = 300, message = "A descrição deve conter entre 10 e 300 caracteres")
    private String description;

    @NotBlank(message = "Campo producer não pode ser vazio")
    @NotNull(message = "Campo producer não pode ser nulo")
    private String producer;

    @NotBlank(message = "Campo platforms não pode ser vazio")
    @NotNull(message = "Campo platforms não pode ser nulo")
    private String platforms;

    private Boolean active;

}
