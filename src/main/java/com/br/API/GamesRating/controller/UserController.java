package com.br.API.GamesRating.controller;

import com.br.API.GamesRating.dto.ListUserDTO;
import com.br.API.GamesRating.dto.NewUserDTO;
import com.br.API.GamesRating.dto.UpdateUserDTO;
import com.br.API.GamesRating.model.UserClient;
import com.br.API.GamesRating.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ListUserDTO> findById(@PathVariable Integer id) {
        var user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping
    public ResponseEntity<Page<ListUserDTO>> findAll() {
        var user = userService.findAll();
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<UserClient> insert(@Valid @RequestBody NewUserDTO newUserDTO) {
        var newUser = userService.insert(newUserDTO);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListUserDTO> update(@Valid @RequestBody UpdateUserDTO userDTO, @PathVariable Integer id) {
        var userUpdate = userService.update(id, userDTO);
        return ResponseEntity.ok().body(userUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
