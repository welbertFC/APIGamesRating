package com.br.API.GamesRating.controller;

import com.br.API.GamesRating.dto.FeedDTO;
import com.br.API.GamesRating.service.FeedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed")
@Api(tags = "Feed")
public class FeedController {

  @Autowired private FeedService feedService;

  @GetMapping
  @ApiOperation(value = "Buscar o feed")
  public ResponseEntity<Page<FeedDTO>> findAll(Pageable pageable) {
    var feed = feedService.findAll(pageable);
    return ResponseEntity.ok(feed);
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<Page<FeedDTO>> findUser(@PathVariable Integer id, Pageable pageable) {
    var feed = feedService.feedByUser(id, pageable);
    return ResponseEntity.ok(feed);
  }
}
