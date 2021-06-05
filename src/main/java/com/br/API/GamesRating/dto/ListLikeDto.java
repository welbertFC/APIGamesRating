package com.br.API.GamesRating.dto;

import com.br.API.GamesRating.model.enums.LikeditEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListLikeDto {

    private Integer idEvaluation;
    private Integer idUser;
    private Integer likeDit;

    public ListLikeDto(Integer idUser, Integer idEvaluation, LikeditEnum likeDit) {
        this.idUser = idUser;
        this.idEvaluation = idEvaluation;
        this.likeDit = likeDit.getCode();
    }
}
