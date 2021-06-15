package com.br.API.GamesRating.repository;

import com.br.API.GamesRating.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

  Page<Note> findByGame_Id(Integer id, Pageable pageable);

  @Query("SELECT AVG(N.note) FROM Note N WHERE N.game.id = ?1")
  Integer avgNote(@PathVariable Integer idGame);
}
