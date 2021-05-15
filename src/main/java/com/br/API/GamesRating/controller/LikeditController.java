package com.br.API.GamesRating.controller;

import com.br.API.GamesRating.dto.NewLikeditDTO;
import com.br.API.GamesRating.dto.UpdateLikeditDTO;
import com.br.API.GamesRating.model.Likedit;
import com.br.API.GamesRating.service.LikeditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/like")
public class LikeditController {

  @Autowired private LikeditService likeditService;

  @PostMapping
  public ResponseEntity<Likedit> insert(@Valid @RequestBody NewLikeditDTO likeditDTO) {
    var likedit = likeditService.insert(likeditDTO);
    var uri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(likedit.getId())
            .toUri();
    return ResponseEntity.created(uri).build();
  }

  @PutMapping("/user/{idUser}/evaluation/{idEvaluation}")
  public ResponseEntity<Likedit> update(
      @Valid @RequestBody UpdateLikeditDTO likeditDTO,
      @PathVariable Integer idUser,
      @PathVariable Integer idEvaluation) {
    var likedit = likeditService.update(idEvaluation, idUser, likeditDTO);
    return ResponseEntity.ok().body(likedit);
  }
}
