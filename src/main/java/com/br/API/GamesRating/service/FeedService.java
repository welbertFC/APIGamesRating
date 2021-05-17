package com.br.API.GamesRating.service;

import com.br.API.GamesRating.dto.FeedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class FeedService {

  @Autowired private LikeditService likeditService;

  @Autowired private EvaluationService evaluationService;

  public Page<FeedDTO> findAll(Pageable pageable) {
    var listEvaluation = evaluationService.findAllFeed(pageable);
    var feed =
        listEvaluation.stream()
            .map(
                obj -> {
                  var likedit = likeditService.sumLike(obj.getId());
                  var dislike = likeditService.sumDisLike(obj.getId());
                  return new FeedDTO(obj, likedit, dislike);
                })
            .sorted(Comparator.comparingInt(FeedDTO::getLike).reversed())
            .collect(Collectors.toList());
    return new PageImpl<>(feed);
  }
}
