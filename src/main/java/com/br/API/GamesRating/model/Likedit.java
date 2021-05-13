package com.br.API.GamesRating.model;

import com.br.API.GamesRating.dto.NewLikeditDTO;
import com.br.API.GamesRating.dto.UpdateLikeditDTO;
import com.br.API.GamesRating.model.enums.LikeditEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class Likedit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer likeDit;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation;


    public Likedit(NewLikeditDTO likeditDTO, Users users, Evaluation evaluation) {
        this.likeDit = likeditDTO.getLikeDit();
        this.users = users;
        this.evaluation = evaluation;
    }

    public Likedit(Integer id, Likedit linkedit, UpdateLikeditDTO likeditDTO) {
        this.id = id;
        this.likeDit = likeditDTO.getLikeDit();
        this.users = linkedit.getUsers();
        this.evaluation = linkedit.getEvaluation();

    }

    public LikeditEnum getLikeDit() {
        return LikeditEnum.toEnum(likeDit);
    }

    public void setLikeDit(Integer likeDit) {
        this.likeDit = likeDit;
    }
}
