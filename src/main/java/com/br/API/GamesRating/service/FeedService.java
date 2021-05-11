package com.br.API.GamesRating.service;

import com.br.API.GamesRating.dto.FeedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedService {

    @Autowired
    private LikeditService likeditService;

    @Autowired
    private EvaluationService evaluationService;

    public List<FeedDTO> findAll(){
        var listEvaluation = evaluationService.findAll();
        var feed = listEvaluation.stream().map(obj -> {
            var likedit = likeditService.sumLike(obj.getId());
            var dislike = likeditService.sumDisLike(obj.getId());
            return new FeedDTO(obj, likedit, dislike);
        }).sorted(Comparator.comparingInt(FeedDTO::getLike)).collect(Collectors.toList());
        return feed;
    }

}
