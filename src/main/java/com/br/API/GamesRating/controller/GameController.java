package com.br.API.GamesRating.controller;

import com.br.API.GamesRating.dto.ListGameDTO;
import com.br.API.GamesRating.dto.NewGameDTO;
import com.br.API.GamesRating.filter.FilterGame;
import com.br.API.GamesRating.model.Game;
import com.br.API.GamesRating.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "Jogo")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    @ApiOperation(value = "Buscas todos os jogo")
    private ResponseEntity<Page<ListGameDTO>> findAll(Pageable pageable) {
        var games = gameService.findAll(pageable);
        return ResponseEntity.ok(games);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Buscar jogo por ID")
    private ResponseEntity<ListGameDTO> findById(@PathVariable Integer id) {
        var game = gameService.findByIdGame(id);
        return ResponseEntity.ok(game);
    }

    @PostMapping
    @ApiOperation(value = "Inserir novo Jogo")
    private ResponseEntity<Void> insert(
            @RequestParam("game") String game, @RequestParam("image") MultipartFile multipartFile) {
        var mapper = new ObjectMapper();
        NewGameDTO newGameDTO;
        try {
            newGameDTO = mapper.readValue(game, NewGameDTO.class);
            var newGame = gameService.insert(newGameDTO, multipartFile);
            var uri =
                    ServletUriComponentsBuilder.fromCurrentRequest()
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
    @ApiOperation(value = "Atualizar Jogo")
    private ResponseEntity<Game> update(
            @Valid @RequestBody NewGameDTO game, @PathVariable Integer id) {
        var updateGame = gameService.update(id, game);
        return ResponseEntity.ok(updateGame);
    }

    @PatchMapping("/{id}/image")
    @ApiOperation(value = "Atualizar Imagem do Jogo")
    public ResponseEntity<Void> saveImage(
            @PathVariable Integer id, @RequestParam(name = "image") MultipartFile multipartFile) {
        var uri = gameService.updateGameImage(id, multipartFile);
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/filter")
    @ApiOperation(value = "filtrar o Jogo")
    public ResponseEntity<Page<ListGameDTO>> searchGame(@RequestBody FilterGame filterGame, Pageable pageable) {
        var listGame = gameService.searchGame(filterGame, pageable);
        return ResponseEntity.ok().body(listGame);
    }
}
