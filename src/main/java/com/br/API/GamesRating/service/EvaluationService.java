package com.br.API.GamesRating.service;

import com.br.API.GamesRating.dto.ListEvaluationDTO;
import com.br.API.GamesRating.dto.NewEvaluationDTO;
import com.br.API.GamesRating.exception.ObjectNotFoundException;
import com.br.API.GamesRating.exception.ObjectNotSaveException;
import com.br.API.GamesRating.model.Evaluation;
import com.br.API.GamesRating.repository.EvaluationRepository;
import com.br.API.GamesRating.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class EvaluationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameService gameService;

    @Autowired
    private EvaluationRepository evaluationRepository;

    public Evaluation insert (NewEvaluationDTO evaluationDTO){
        var evaluation = validationInsertEvaluation(evaluationDTO);
        return evaluationRepository.save(evaluation);
   }

    private List<Evaluation> findAll(){
        return evaluationRepository.findAll();
    }

    public List<ListEvaluationDTO> findAllByGame(Integer id){
        var evaluation = evaluationRepository.findByGame_Id(id);
        var listEvaluation = evaluation.stream().map(obj -> new ListEvaluationDTO(obj)).collect(Collectors.toList());
        return listEvaluation;
    }

    public List<ListEvaluationDTO> findAllByUser(Integer id){
        var evaluation = evaluationRepository.findByUser_Id(id);
        var listEvaluation = evaluation.stream().map(obj -> new ListEvaluationDTO(obj)).collect(Collectors.toList());
        return listEvaluation;
    }

    private Evaluation validationInsertEvaluation(NewEvaluationDTO evaluationDTO){
        var userOptional = userRepository.findById(evaluationDTO.getUser());
        var game = gameService.findById(evaluationDTO.getGame());
        var user = userOptional.orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado id: " + evaluationDTO.getUser()));
        var evaluations = findAll();
        evaluations.forEach(obj -> {
            if (obj.getUser().getId().equals(evaluationDTO.getUser()) && obj.getGame().getId().equals(evaluationDTO.getGame())){
                throw new ObjectNotSaveException("O Usuario: " + obj.getUser().getName() + " Já fez uma resenha sobre este jogo");
            }
        });

        return new Evaluation(evaluationDTO, user, game);
    }


}
