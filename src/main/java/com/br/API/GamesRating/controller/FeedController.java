package com.br.API.GamesRating.controller;

import com.br.API.GamesRating.dto.FeedDTO;
import com.br.API.GamesRating.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private FeedService feedService;

    @GetMapping
    public ResponseEntity<List<FeedDTO>> findAll(){
        var feed = feedService.findAll();
        return ResponseEntity.ok().body(feed);
    }


}
