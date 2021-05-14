package com.br.API.GamesRating.controller;

import com.br.API.GamesRating.dto.ListGameDTO;
import com.br.API.GamesRating.dto.NewGameDTO;
import com.br.API.GamesRating.model.Game;
import com.br.API.GamesRating.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    private ResponseEntity<Page<ListGameDTO>> findAll(Pageable pageable) {
        var games = gameService.findAll(pageable);
        return ResponseEntity.ok().body(games);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ListGameDTO> findById(@PathVariable Integer id) {
        var game = gameService.findByIdGame(id);
        return ResponseEntity.ok().body(game);
    }

    @PostMapping
    private ResponseEntity<Void> insert(@RequestParam("game") String game, @RequestParam("image") MultipartFile multipartFile) {
        var mapper = new ObjectMapper();
        NewGameDTO newGameDTO;
        try {
            newGameDTO = mapper.readValue(game, NewGameDTO.class);
            var newGame = gameService.insert(newGameDTO, multipartFile);
            var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newGame.getId())
                    .toUri();
            return ResponseEntity.created(uri).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping("/{id}")
    private ResponseEntity<Game> update(@Valid @RequestBody NewGameDTO game, @PathVariable Integer id) {
        var updateGame = gameService.update(id, game);
        return ResponseEntity.ok().body(updateGame);
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<Void> saveImage(@PathVariable Integer id, @RequestParam(name = "image") MultipartFile multipartFile) {
        var uri = gameService.updateGameImage(id, multipartFile);
        return ResponseEntity.created(uri).build();
    }


}
