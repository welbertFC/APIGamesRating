package com.br.API.GamesRating.dto;

import com.br.API.GamesRating.model.UserClient;
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
  private String urlImage;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate birthDate;

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  private LocalDateTime dateCreated;

  public ListUserDTO(Optional<UserClient> user) {
    this.id = user.get().getId();
    this.name = user.get().getName();
    this.nickName = user.get().getNickName();
    this.email = user.get().getEmail();
    this.birthDate = user.get().getBirthDate();
    this.dateCreated = user.get().getDateCreated();
    this.urlImage = user.get().getUrlImage();
  }

  public ListUserDTO(UserClient userClient) {
    this.id = userClient.getId();
    this.name = userClient.getName();
    this.nickName = userClient.getNickName();
    this.email = userClient.getEmail();
    this.birthDate = userClient.getBirthDate();
    this.dateCreated = userClient.getDateCreated();
    this.urlImage = userClient.getUrlImage();
  }
}
