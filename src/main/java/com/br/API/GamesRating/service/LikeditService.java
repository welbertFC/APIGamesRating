package com.br.API.GamesRating.service;

import com.br.API.GamesRating.dto.NewLikeditDTO;
import com.br.API.GamesRating.dto.UpdateLikeditDTO;
import com.br.API.GamesRating.exception.ObjectNotFoundException;
import com.br.API.GamesRating.exception.ObjectNotSaveException;
import com.br.API.GamesRating.model.Likedit;
import com.br.API.GamesRating.model.enums.LikeditEnum;
import com.br.API.GamesRating.repository.LikeditRepository;
import com.br.API.GamesRating.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeditService {

    @Autowired
    private LikeditRepository likeditRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EvaluationService evaluationService;

    public Likedit insert(NewLikeditDTO likeditDTO) {
        var likedit = validationInsert(likeditDTO);
        return likeditRepository.save(likedit);
    }

    private List<Likedit> findall() {
        return likeditRepository.findAll();
    }

    private Likedit findById(Integer id){
        var linkdit =  likeditRepository.findById(id);
        return linkdit.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado id: " + id));
    }

    public Likedit update(Integer id, UpdateLikeditDTO likeditDTO){
       var linkedit =  findById(id);
       return likeditRepository.save(new Likedit(id, linkedit, likeditDTO));
    }

    public Integer sumLike(Integer idEvaluation){
        var listLike = likeditRepository.findByEvaluation_Id(idEvaluation);
        var likeList =  listLike.stream().filter(obj -> obj.getLikeDit().equals(LikeditEnum.LIKE)).collect(Collectors.toList());
        var sumlike = 0;
        var like = likeList.size();
        while (like > 0){
            sumlike += 1;
            like --;
        }
        return sumlike;
    }

    public Integer sumDisLike(Integer idEvaluation){
        var listLike = likeditRepository.findByEvaluation_Id(idEvaluation);
        var dislike = listLike.stream().filter(obj -> obj.getLikeDit().equals(LikeditEnum.DISLIKE)).collect(Collectors.toList());
        var sumDislike = 0;
        var like = dislike.size();
        while (like > 0){
            sumDislike += 1;
            like --;
        }
        return sumDislike;
    }

    private Likedit validationInsert(NewLikeditDTO likeditDTO) {
        var userOptional = userRepository.findById(likeditDTO.getUser());
        var user = userOptional.orElseThrow(() -> new ObjectNotSaveException("Usuario Não encontrado id: " + likeditDTO.getUser()));
        var evaluation = evaluationService.findById(likeditDTO.getEvaluation());
        var likes = likeditRepository.findAll();
        likes.forEach(obj -> {
            if (obj.getUser().getId().equals(likeditDTO.getUser()) && obj.getEvaluation().getId().equals(likeditDTO.getEvaluation())){
                throw new ObjectNotSaveException("Usuario " + obj.getUser().getName()+ " Já curtiu esta resenha");
            }
        });

        return new Likedit(likeditDTO, user, evaluation);
    }
}
