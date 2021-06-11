package com.br.API.GamesRating.controller;

import com.br.API.GamesRating.dto.NewNoteDTO;
import com.br.API.GamesRating.model.Note;
import com.br.API.GamesRating.service.NoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/note")
@Api(tags = "Nota do Jogo")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping
    @ApiOperation(value = "Insert game note")
    public ResponseEntity<Note> insert(@Valid @RequestBody NewNoteDTO newNoteDTO) {
        var note = noteService.insert(newNoteDTO);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(note.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/game/{id}")
    @ApiOperation(value = "find all games note by Id game")
    public ResponseEntity<Page<Note>> findByNoteGame(@PathVariable Integer id, Pageable pageable) {
        var note = noteService.findByIdGame(id, pageable);
        return ResponseEntity.ok(note);
    }
}
