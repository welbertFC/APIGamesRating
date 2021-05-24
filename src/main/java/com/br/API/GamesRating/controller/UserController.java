package com.br.API.GamesRating.controller;

import com.br.API.GamesRating.dto.ListUserDTO;
import com.br.API.GamesRating.dto.NewUserDTO;
import com.br.API.GamesRating.dto.UpdateUserDTO;
import com.br.API.GamesRating.service.UserClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserClientService userClientService;

    @GetMapping("/{id}")
    public ResponseEntity<ListUserDTO> findById(@PathVariable Integer id) {
        var user = userClientService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<Page<ListUserDTO>> findAll() {
        var user = userClientService.findAll();
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<ListUserDTO> insert(@Valid @RequestBody NewUserDTO newUserDTO) {
        var newUser = userClientService.insert(newUserDTO);
        return ResponseEntity.ok(newUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListUserDTO> update(@Valid @RequestBody UpdateUserDTO userDTO, @PathVariable Integer id) {
        var userUpdate = userClientService.update(id, userDTO);
        return ResponseEntity.ok(userUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userClientService.delete(id);
        return ResponseEntity.ok().build();
    }
}
