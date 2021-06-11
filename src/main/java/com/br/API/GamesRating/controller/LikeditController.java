package com.br.API.GamesRating.controller;

import com.br.API.GamesRating.dto.ListLikeDto;
import com.br.API.GamesRating.dto.NewLikeditDTO;
import com.br.API.GamesRating.model.Likedit;
import com.br.API.GamesRating.service.LikeditService;
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
@RequestMapping("/like")
@Api(tags = "Curtida")
public class LikeditController {

    @Autowired
    private LikeditService likeditService;

    @PostMapping
    @ApiOperation(value = "Insert and update like")
    public ResponseEntity<Likedit> insert(@Valid @RequestBody NewLikeditDTO likeditDTO) {
        var likedit = likeditService.insert(likeditDTO);
        var uri =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(likedit.getId())
                        .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/user/{id}")
    @ApiOperation(value = "Find all likes by ID user")
    public ResponseEntity<Page<ListLikeDto>> findallByIdUser(
            @PathVariable Integer id, Pageable pageable) {
        var listLike = likeditService.listLikeByUser(id, pageable);
        return ResponseEntity.ok().body(listLike);
    }
}
