package com.br.API.GamesRating.model;


import com.br.API.GamesRating.dto.ListUserDTO;
import com.br.API.GamesRating.dto.NewUserDTO;
import com.br.API.GamesRating.dto.UpdateUserDTO;
import com.br.API.GamesRating.model.enums.UserProfile;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PROFILE")
    private Set<Integer> profile = new HashSet<>();

    private LocalDateTime dateCreated;
    private String urlImage;

    @OneToMany(mappedBy = "userClient", cascade = CascadeType.ALL)
    private List<Note> notes = new ArrayList<>();

    @OneToMany(mappedBy = "userClient", cascade = CascadeType.ALL)
    private List<Evaluation> evaluations = new ArrayList<>();

    @OneToMany(mappedBy = "userClient", cascade = CascadeType.ALL)
    private List<Likedit> likedits = new ArrayList<>();



    public UserClient(NewUserDTO userDTO, String password) {
        this.name = userDTO.getName();
        this.nickName = userDTO.getNickName();
        this.email = userDTO.getEmail();
        this.password = password;
        this.birthDate = userDTO.getBirthDate();
        this.dateCreated = LocalDateTime.now();
        this.urlImage = userDTO.getUrlImage();
        addPerfil(UserProfile.USER);
    }

    public UserClient(Integer id, UpdateUserDTO userDTO, ListUserDTO listUserDTO) {
        this.id = id;
        this.name = userDTO.getName();
        this.nickName = userDTO.getNickName();
        this.email = listUserDTO.getEmail();
        this.password = userDTO.getPassword();
        this.birthDate = userDTO.getBirthDate();
        this.dateCreated = listUserDTO.getDateCreated();
        this.urlImage = listUserDTO.getUrlImage();
    }


    public Set<UserProfile> getProfile() {
        return profile.stream().map(x -> UserProfile.toEnum(x)).collect(Collectors.toSet());

    }

    public void addPerfil(UserProfile perfil) {
        profile.add(perfil.getCode());
    }
}
