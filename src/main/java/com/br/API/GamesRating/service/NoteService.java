package com.br.API.GamesRating.service;

import com.br.API.GamesRating.dto.NewNoteDTO;
import com.br.API.GamesRating.exception.ObjectNotSaveException;
import com.br.API.GamesRating.model.Note;
import com.br.API.GamesRating.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    public Note insert(NewNoteDTO newNoteDTO) {
        var note = validationNote(newNoteDTO);
        return noteRepository.save(note);
    }

    private List<Note> findAll() {
        return noteRepository.findAll();
    }

    public List<Note> findByIdGame(Integer id) {
        return noteRepository.findByGame_Id(id);
    }

    private Note validationNote(NewNoteDTO newNoteDTO) {
        var user = userService.findByIdUser(newNoteDTO.getUser());
        var game = gameService.findById(newNoteDTO.getGame());
        var notes = findAll();
        notes.forEach(obj -> {
            if (obj.getUser().getId().equals(newNoteDTO.getUser()) && obj.getGame().getId().equals(newNoteDTO.getGame())) {
                throw new ObjectNotSaveException("O Usuario: " + obj.getUser().getName()
                        + " Já avaliou o jogo: " + obj.getGame().getTitle());
            }
        });
        return new Note(newNoteDTO, user, game);
    }
}
