package com.br.API.GamesRating.service;

import com.br.API.GamesRating.dto.GameDTO;
import com.br.API.GamesRating.exception.ObjectNotFoundException;
import com.br.API.GamesRating.model.Game;
import com.br.API.GamesRating.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public Game findById(Integer id){
        var game = gameRepository.findById(id);
        return game.orElseThrow(() -> new ObjectNotFoundException("Game não encontrado id: " + id));
    }

    public List<Game> findAll(){
        var games = gameRepository.findAll();
        return games.stream().filter(obj ->
                obj.getActive().equals(true)).collect(Collectors.toList());
    }


    public Game insert(GameDTO game){
        return gameRepository.save(new Game(game));
    }

    public Game update(Integer id, GameDTO game){
        findById(id);
        return gameRepository.save(new Game(id, game));
    }

}
