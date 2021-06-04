package com.br.API.GamesRating.controller;

import com.br.API.GamesRating.dto.ListUserDTO;
import com.br.API.GamesRating.dto.NewUserDTO;
import com.br.API.GamesRating.dto.UpdateUserDTO;
import com.br.API.GamesRating.service.UserClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Api(tags = "Usuario")
public class UserController {

    @Autowired
    private UserClientService userClientService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca o usuario por ID")
    public ResponseEntity<ListUserDTO> findById(@PathVariable Integer id) {
        var user = userClientService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    @ApiOperation(value = "Busca todos os usuarios")
    public ResponseEntity<Page<ListUserDTO>> findAll() {
        var user = userClientService.findAll();
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @ApiOperation(value = "Inseri um novo Usuario")
    public ResponseEntity<ListUserDTO> insert(@Valid @RequestBody NewUserDTO newUserDTO) {
        var newUser = userClientService.insert(newUserDTO);
        return ResponseEntity.ok(newUser);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza um usuario")
    public ResponseEntity<ListUserDTO> update(@Valid @RequestBody UpdateUserDTO userDTO, @PathVariable Integer id) {
        var userUpdate = userClientService.update(id, userDTO);
        return ResponseEntity.ok(userUpdate);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta um usuario")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userClientService.delete(id);
        return ResponseEntity.ok().build();
    }
}
