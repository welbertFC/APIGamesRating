package com.br.API.GamesRating.controller;

import com.br.API.GamesRating.dto.NewNoteDTO;
import com.br.API.GamesRating.model.Note;
import com.br.API.GamesRating.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping
    public ResponseEntity<Note> insert(@Valid @RequestBody NewNoteDTO newNoteDTO){
        var note = noteService.insert(newNoteDTO);
        return ResponseEntity.ok().body(note);
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<List<Note>> findByNoteGame(@PathVariable Integer id){
        var note = noteService.findByIdGame(id);
        return ResponseEntity.ok().body(note);
    }
}
