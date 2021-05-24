package com.br.API.GamesRating.service;

import com.br.API.GamesRating.dto.NewLikeditDTO;
import com.br.API.GamesRating.exception.ObjectNotFoundException;
import com.br.API.GamesRating.exception.ObjectNotSaveException;
import com.br.API.GamesRating.model.Likedit;
import com.br.API.GamesRating.model.enums.LikeditEnum;
import com.br.API.GamesRating.repository.LikeditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class LikeditService {

  @Autowired private LikeditRepository likeditRepository;

  @Autowired private UserClientService userClientService;

  @Autowired private EvaluationService evaluationService;

  public Likedit insert(NewLikeditDTO likeditDTO) {
    var like =
        likeditRepository.findByUserClient_IdAndAndEvaluation_Id(
            likeditDTO.getUser(), likeditDTO.getEvaluation());
    if (like == null) {
      var newLike = validationInsert(likeditDTO);
      return likeditRepository.save(newLike);
    } else return update(likeditDTO);
  }

  public Likedit update(NewLikeditDTO likeditDTO) {
    userClientService.findByIdUser(likeditDTO.getUser());
    evaluationService.findById(likeditDTO.getEvaluation());
    var linkedit =
        likeditRepository.findByUserClient_IdAndAndEvaluation_Id(
            likeditDTO.getUser(), likeditDTO.getEvaluation());
    if (linkedit == null) throw new ObjectNotFoundException("Resenha ainda não foi Avaliada");
    return likeditRepository.save(new Likedit(linkedit.getId(), linkedit, likeditDTO.getLikeDit()));
  }

  public Integer sumLike(Integer idEvaluation) {
    var listLike = likeditRepository.findByEvaluation_Id(idEvaluation);
    var likeList =
        listLike.stream()
            .filter(obj -> obj.getLikeDit().equals(LikeditEnum.LIKE))
            .collect(Collectors.toList());
    var sumlike = 0;
    var like = likeList.size();
    while (like > 0) {
      sumlike += 1;
      like--;
    }
    return sumlike;
  }

  public Integer sumDisLike(Integer idEvaluation) {
    var listLike = likeditRepository.findByEvaluation_Id(idEvaluation);
    var dislike =
        listLike.stream()
            .filter(obj -> obj.getLikeDit().equals(LikeditEnum.DISLIKE))
            .collect(Collectors.toList());
    var sumDislike = 0;
    var like = dislike.size();
    while (like > 0) {
      sumDislike += 1;
      like--;
    }
    return sumDislike;
  }

  private Likedit validationInsert(NewLikeditDTO likeditDTO) {
    var user = userClientService.findByIdUser(likeditDTO.getUser());
    var evaluation = evaluationService.findById(likeditDTO.getEvaluation());
    var likes = likeditRepository.findAll();
    likes.forEach(
        obj -> {
          if (obj.getUserClient().getId().equals(likeditDTO.getUser())
              && obj.getEvaluation().getId().equals(likeditDTO.getEvaluation())) {
            throw new ObjectNotSaveException(
                "Usuario " + obj.getUserClient().getName() + " Já curtiu esta resenha");
          }
        });

    return new Likedit(likeditDTO, user, evaluation);
  }
}
