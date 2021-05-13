package com.br.API.GamesRating.service;

import com.br.API.GamesRating.dto.NewLikeditDTO;
import com.br.API.GamesRating.dto.UpdateLikeditDTO;
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

    @Autowired
    private LikeditRepository likeditRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EvaluationService evaluationService;

    public Likedit insert(NewLikeditDTO likeditDTO) {
        var likedit = validationInsert(likeditDTO);
        return likeditRepository.save(likedit);
    }

    public Likedit update(Integer idEvaluation, Integer idUser, UpdateLikeditDTO likeditDTO) {
        userService.findByIdUser(idUser);
        evaluationService.findById(idEvaluation);
        var linkedit = likeditRepository.findByUser_IdAndAndEvaluation_Id(idUser, idEvaluation);
        if(linkedit == null) throw new ObjectNotFoundException("Resenha ainda não foi Avaliada");
        return likeditRepository.save(new Likedit(linkedit.getId(), linkedit, likeditDTO));
    }

    public Integer sumLike(Integer idEvaluation) {
        var listLike = likeditRepository.findByEvaluation_Id(idEvaluation);
        var likeList = listLike.stream().filter(obj -> obj.getLikeDit().equals(LikeditEnum.LIKE)).collect(Collectors.toList());
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
        var dislike = listLike.stream().filter(obj -> obj.getLikeDit().equals(LikeditEnum.DISLIKE)).collect(Collectors.toList());
        var sumDislike = 0;
        var like = dislike.size();
        while (like > 0) {
            sumDislike += 1;
            like--;
        }
        return sumDislike;
    }

    private Likedit validationInsert(NewLikeditDTO likeditDTO) {
        var user = userService.findByIdUser(likeditDTO.getUser());
        var evaluation = evaluationService.findById(likeditDTO.getEvaluation());
        var likes = likeditRepository.findAll();
        likes.forEach(obj -> {
            if (obj.getUsers().getId().equals(likeditDTO.getUser()) && obj.getEvaluation().getId().equals(likeditDTO.getEvaluation())) {
                throw new ObjectNotSaveException("Usuario " + obj.getUsers().getName() + " Já curtiu esta resenha");
            }
        });

        return new Likedit(likeditDTO, user, evaluation);
    }
}
