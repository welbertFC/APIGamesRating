package com.br.API.GamesRating.service;

import com.br.API.GamesRating.dto.ListEvaluationDTO;
import com.br.API.GamesRating.dto.NewEvaluationDTO;
import com.br.API.GamesRating.exception.ObjectNotFoundException;
import com.br.API.GamesRating.exception.ObjectNotSaveException;
import com.br.API.GamesRating.model.Evaluation;
import com.br.API.GamesRating.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class EvaluationService {

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private LikeditService likeditService;

    public Evaluation insert(NewEvaluationDTO evaluationDTO) {
        var evaluation = validationInsertEvaluation(evaluationDTO);
        return evaluationRepository.save(evaluation);
    }

    public Evaluation findById(Integer id) {
        var evaluation = evaluationRepository.findById(id);
        return evaluation.orElseThrow(() -> new ObjectNotFoundException("Avaliação nao encontarda id: " + id));
    }

    public List<Evaluation> findAll() {
        return evaluationRepository.findAll();
    }

    public List<ListEvaluationDTO> findAllByGame(Integer id) {
        var evaluation = evaluationRepository.findByGame_Id(id);
        return getListEvaluationDTOS(evaluation);
    }

    public List<ListEvaluationDTO> findAllByUser(Integer id) {
        var evaluation = evaluationRepository.findByUser_Id(id);
        return getListEvaluationDTOS(evaluation);
    }

    private List<ListEvaluationDTO> getListEvaluationDTOS(List<Evaluation> evaluation) {
        var listEvaluation = evaluation.stream().map(obj -> {
            var likedit = likeditService.sumLike(obj.getId());
            var dislike = likeditService.sumDisLike(obj.getId());
            return new ListEvaluationDTO(obj, likedit, dislike);
        }).collect(Collectors.toList());
        return listEvaluation;
    }

    private Evaluation validationInsertEvaluation(NewEvaluationDTO evaluationDTO) {
        var user = userService.findByIdUser(evaluationDTO.getUser());
        var game = gameService.findById(evaluationDTO.getGame());
        var evaluations = findAll();
        evaluations.forEach(obj -> {
            if (obj.getUser().getId().equals(evaluationDTO.getUser()) && obj.getGame().getId().equals(evaluationDTO.getGame())) {
                throw new ObjectNotSaveException("O Usuario: " + obj.getUser().getName() + " Já fez uma resenha sobre este jogo");
            }
        });

        return new Evaluation(evaluationDTO, user, game);
    }


}
