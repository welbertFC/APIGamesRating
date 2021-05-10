package com.br.API.GamesRating.controller;

import com.br.API.GamesRating.dto.ListEvaluationDTO;
import com.br.API.GamesRating.dto.NewEvaluationDTO;
import com.br.API.GamesRating.model.Evaluation;
import com.br.API.GamesRating.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping
    public ResponseEntity<Evaluation> insert(@Valid @RequestBody NewEvaluationDTO evaluationDTO){
        var evaluation = evaluationService.insert(evaluationDTO);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(evaluation.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ListEvaluationDTO>> findEvaluationByUser(@PathVariable Integer id){
        var evaluation = evaluationService.findAllByUser(id);
        return ResponseEntity.ok().body(evaluation);
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<List<ListEvaluationDTO>> findEvaluationByGame(@PathVariable Integer id){
        var evaluation = evaluationService.findAllByGame(id);
        return ResponseEntity.ok().body(evaluation);
    }


}
