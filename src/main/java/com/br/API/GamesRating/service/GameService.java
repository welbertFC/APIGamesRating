package com.br.API.GamesRating.service;

import com.br.API.GamesRating.dto.ListGameDTO;
import com.br.API.GamesRating.dto.NewGameDTO;
import com.br.API.GamesRating.exception.ObjectNotFoundException;
import com.br.API.GamesRating.model.Game;
import com.br.API.GamesRating.repository.GameRepository;
import com.br.API.GamesRating.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private S3service s3service;

    @Autowired
    private ImageService imageService;

    public Game findById(Integer id) {
        var gameOptional = gameRepository.findById(id);
        return gameOptional.orElseThrow(() -> new ObjectNotFoundException("Game não encontrado id: " + id));

    }

    public ListGameDTO findByIdGame(Integer id) {
        var gameOptional = gameRepository.findById(id);
        var game = gameOptional.orElseThrow(() -> new ObjectNotFoundException("Game não encontrado id: " + id));
        var note = noteRepository.avgNote(game.getId());
        return new ListGameDTO(note, game);
    }

    public Page<ListGameDTO> findAll(Pageable pageable) {
        var games = gameRepository.findAll(pageable);
        var gameslist = games.stream().map(obj -> {
            var note = noteRepository.avgNote(obj.getId());
            return new ListGameDTO(note, obj);
        }).collect(Collectors.toList());
        return new PageImpl<>(gameslist);
    }


    public Game insert(NewGameDTO game) {
        return gameRepository.save(new Game(game));
    }

    public Game update(Integer id, NewGameDTO game) {
        findById(id);
        return gameRepository.save(new Game(id, game));
    }

    public URI uploadProfilePicture(Integer id, MultipartFile multipartFile) {
        var game = findById(id);
        var jpgImage = imageService.getJpgImagemFromFile(multipartFile);
        var fileName = game.getTitle() + ".jpg";
        var uri = s3service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
        game.setUrlImage(uri.toString());
        gameRepository.save(game);
        return uri;
    }


}
