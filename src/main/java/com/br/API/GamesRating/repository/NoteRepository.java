package com.br.API.GamesRating.repository;

import com.br.API.GamesRating.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

    List<Note> findByGame_Id(Integer id);
}
