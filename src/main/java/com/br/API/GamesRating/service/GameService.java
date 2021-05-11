package com.br.API.GamesRating.service;

import com.br.API.GamesRating.dto.ListGameDTO;
import com.br.API.GamesRating.dto.NewGameDTO;
import com.br.API.GamesRating.exception.ObjectNotFoundException;
import com.br.API.GamesRating.model.Game;
import com.br.API.GamesRating.repository.GameRepository;
import com.br.API.GamesRating.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private NoteRepository noteRepository;

    public Game findById(Integer id) {
        var gameOptional = gameRepository.findById(id);
        return gameOptional.orElseThrow(() -> new ObjectNotFoundException("Game não encontrado id: " + id));

    }

    public ListGameDTO findByIdGame(Integer id) {
        var gameOptional = gameRepository.findById(id);
        var game = gameOptional.orElseThrow(() -> new ObjectNotFoundException("Game não encontrado id: " + id));
        var note = noteRepository.avgNote(game.getId());
        return new ListGameDTO(note, game);
    }

    public List<ListGameDTO> findAll() {
        var games = gameRepository.findAll();
        var gameslist = games.stream().map(obj -> {
            var note = noteRepository.avgNote(obj.getId());
            return new ListGameDTO(note, obj);
        }).collect(Collectors.toList());
        return gameslist;
    }


    public Game insert(NewGameDTO game) {
        return gameRepository.save(new Game(game));
    }

    public Game update(Integer id, NewGameDTO game) {
        findById(id);
        return gameRepository.save(new Game(id, game));
    }

}
