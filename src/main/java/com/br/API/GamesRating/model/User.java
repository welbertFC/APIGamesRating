package com.br.API.GamesRating.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String nickName;
    private String email;
    private String password;
    private Date birthDate;
    private String type;
    private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "user")
    private List<Note> notes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Evaluation> evaluations = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Likedit> likedits = new ArrayList<>();
}
