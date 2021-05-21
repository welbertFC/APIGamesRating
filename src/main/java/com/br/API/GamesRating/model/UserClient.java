package com.br.API.GamesRating.model;


import com.br.API.GamesRating.dto.ListUserDTO;
import com.br.API.GamesRating.dto.NewUserDTO;
import com.br.API.GamesRating.dto.UpdateUserDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class UserClient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true)
    private String nickName;

    @Column(unique = true)
    private String email;
    private String password;
    private LocalDate birthDate;
    private String type;
    private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "userClient", cascade = CascadeType.ALL)
    private List<Note> notes = new ArrayList<>();

    @OneToMany(mappedBy = "userClient", cascade = CascadeType.ALL)
    private List<Evaluation> evaluations = new ArrayList<>();

    @OneToMany(mappedBy = "userClient", cascade = CascadeType.ALL)
    private List<Likedit> likedits = new ArrayList<>();

    public UserClient(NewUserDTO userDTO) {
        this.name = userDTO.getName();
        this.nickName = userDTO.getNickName();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.birthDate = userDTO.getBirthDate();
        this.type = "U";
        this.dateCreated = LocalDateTime.now();
    }

    public UserClient(Integer id, UpdateUserDTO userDTO, ListUserDTO listUserDTO) {
        this.id = id;
        this.name = userDTO.getName();
        this.nickName = userDTO.getNickName();
        this.email = listUserDTO.getEmail();
        this.password = userDTO.getPassword();
        this.birthDate = userDTO.getBirthDate();
        this.type = listUserDTO.getType();
        this.dateCreated = listUserDTO.getDateCreated();
    }
}
