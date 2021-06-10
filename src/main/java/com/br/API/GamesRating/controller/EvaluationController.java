package com.br.API.GamesRating.controller;

import com.br.API.GamesRating.dto.ListEvaluationDTO;
import com.br.API.GamesRating.dto.ListUpdateEvaluationDTO;
import com.br.API.GamesRating.dto.NewEvaluationDTO;
import com.br.API.GamesRating.dto.UpdateEvaluationDTO;
import com.br.API.GamesRating.model.Evaluation;
import com.br.API.GamesRating.service.EvaluationService;
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
@RequestMapping("/evaluation")
@Api(tags = "Resenha")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;


    @PostMapping
    @ApiOperation(value = "Inserir nova resenha")
    public ResponseEntity<Evaluation> insert(@Valid @RequestBody NewEvaluationDTO evaluationDTO) {
        var evaluation = evaluationService.insert(evaluationDTO);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(evaluation.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }


    @GetMapping("/user/{id}")
    @ApiOperation(value = "Buscar resenhas feitas pelo ID do usuario")
    public ResponseEntity<Page<ListEvaluationDTO>> findEvaluationByUser(@PathVariable Integer id, Pageable pageable) {
        var evaluation = evaluationService.findAllByUser(id, pageable);
        return ResponseEntity.ok(evaluation);
    }


    @GetMapping("/game/{id}")
    @ApiOperation(value = "Buscar resenhas feitas pelo ID do Jogo")
    public ResponseEntity<Page<ListEvaluationDTO>> findEvaluationByGame(@PathVariable Integer id, Pageable pageable) {
        var evaluation = evaluationService.findAllByGame(id, pageable);
        return ResponseEntity.ok(evaluation);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza resenhas")
    public ResponseEntity<ListUpdateEvaluationDTO> updateEvaluation (@Valid @RequestBody UpdateEvaluationDTO updateEvaluationDTO, @PathVariable Integer id){
        var upEvaluation = evaluationService.updateEvaluation(id, updateEvaluationDTO);
        return ResponseEntity.ok(upEvaluation);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta resenhas")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Integer id){
        evaluationService.deleteEvaluation(id);
        return ResponseEntity.ok().build();
    }


}
