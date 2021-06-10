package com.br.API.GamesRating.service;

import com.br.API.GamesRating.dto.ListEvaluationDTO;
import com.br.API.GamesRating.dto.NewEvaluationDTO;
import com.br.API.GamesRating.dto.UpdateEvaluationDTO;
import com.br.API.GamesRating.exception.ObjectNotFoundException;
import com.br.API.GamesRating.exception.ObjectNotSaveException;
import com.br.API.GamesRating.model.Evaluation;
import com.br.API.GamesRating.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvaluationService {

  @Autowired private UserClientService userClientService;

  @Autowired private GameService gameService;

  @Autowired private EvaluationRepository evaluationRepository;

  @Autowired private LikeditService likeditService;

  public Evaluation insert(NewEvaluationDTO evaluationDTO) {
    var evaluation = validationInsertEvaluation(evaluationDTO);
    return evaluationRepository.save(evaluation);
  }

  public Evaluation findById(Integer id) {
    var evaluation = evaluationRepository.findById(id);
    return evaluation.orElseThrow(
        () -> new ObjectNotFoundException("Avaliação nao encontarda id: " + id));
  }

  public List<Evaluation> findAll() {
    return evaluationRepository.findAll();
  }

  public Page<Evaluation> findAllFeed(Pageable pageable) {
    return evaluationRepository.findAll(pageable);
  }

  public Page<ListEvaluationDTO> findAllByGame(Integer id, Pageable pageable) {
    var evaluation = evaluationRepository.findByGame_Id(id, pageable);
    return getListEvaluationDTOS(evaluation);
  }

  public Page<ListEvaluationDTO> findAllByUser(Integer id, Pageable pageable) {
    var evaluation = evaluationRepository.findByUserClient_Id(id, pageable);
    return getListEvaluationDTOS(evaluation);
  }

  private Page<ListEvaluationDTO> getListEvaluationDTOS(Page<Evaluation> evaluation) {
    var listEvaluation =
        evaluation.stream()
            .map(
                obj -> {
                  var likedit = likeditService.sumLike(obj.getId());
                  var dislike = likeditService.sumDisLike(obj.getId());
                  return new ListEvaluationDTO(obj, likedit, dislike);
                })
            .collect(Collectors.toList());
    return new PageImpl<>(listEvaluation);
  }

  public void deleteEvaluation(Integer id){
    var evaluation = findById(id);
    evaluationRepository.delete(evaluation);
  }

  public ListEvaluationDTO updateEvaluation (Integer id, UpdateEvaluationDTO evaluationDTO){
    var evaluation = findById(id);
    var newEvaluation = evaluationRepository.save(new Evaluation(id ,evaluation, evaluationDTO));
    return new ListEvaluationDTO(newEvaluation);

  }

  private Evaluation validationInsertEvaluation(NewEvaluationDTO evaluationDTO) {
    var user = userClientService.findByIdUser(evaluationDTO.getUser());
    var game = gameService.findById(evaluationDTO.getGame());
    var evaluations = findAll();
    evaluations.forEach(
        obj -> {
          if (obj.getUserClient().getId().equals(evaluationDTO.getUser())
              && obj.getGame().getId().equals(evaluationDTO.getGame())) {
            throw new ObjectNotSaveException("Você já fez uma resenha sobre este jogo");
          }
        });

    return new Evaluation(evaluationDTO, user, game);
  }
}
