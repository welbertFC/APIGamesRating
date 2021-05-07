package com.br.API.GamesRating.controller;

import com.br.API.GamesRating.dto.GameDTO;
import com.br.API.GamesRating.model.Game;
import com.br.API.GamesRating.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    private ResponseEntity<List<Game>> findAll(){
        var games = gameService.findAll();
        return ResponseEntity.ok().body(games);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Game> findById(@PathVariable Integer id){
        var game = gameService.findById(id);
        return ResponseEntity.ok().body(game);
    }

    @PostMapping
    private ResponseEntity<Game> insert(@Valid @RequestBody GameDTO game){
        var newGame = gameService.insert(game);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newGame.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<Game> update(@Valid @RequestBody GameDTO game, @PathVariable Integer id){
        var updateGame = gameService.update(id, game);
        return ResponseEntity.ok().body(updateGame);
    }


}
