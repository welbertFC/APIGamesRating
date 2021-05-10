package com.br.API.GamesRating.dto;

import com.br.API.GamesRating.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@NoArgsConstructor
public class ListUserDTO {

    private Integer id;
    private String name;
    private String nickName;
    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    private String type;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dateCreated;

    public ListUserDTO(Optional<User> user) {
        this.id = user.get().getId();
        this.name = user.get().getName();
        this.nickName = user.get().getNickName();
        this.email = user.get().getEmail();
        this.birthDate = user.get().getBirthDate();
        this.type = user.get().getType();
        this.dateCreated = user.get().getDateCreated();
    }

    public ListUserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.birthDate = user.getBirthDate();
        this.type = user.getType();
        this.dateCreated = user.getDateCreated();
    }
}


