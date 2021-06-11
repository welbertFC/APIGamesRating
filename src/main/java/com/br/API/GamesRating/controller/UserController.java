package com.br.API.GamesRating.controller;

import com.br.API.GamesRating.dto.*;
import com.br.API.GamesRating.service.UserClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Api(tags = "Usuario")
public class UserController {

  @Autowired private UserClientService userClientService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Find by ID user")
    public ResponseEntity<ListUserDTO> findById(@PathVariable Integer id) {
        var user = userClientService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    @ApiOperation(value = "Find all users")
    public ResponseEntity<Page<ListUserDTO>> findAll() {
        var user = userClientService.findAll();
        return ResponseEntity.ok(user);
    }

  @ApiOperation(value = "Insert new user")
  @PostMapping
  public ResponseEntity<ListUserDTO> insert(@Valid @RequestBody NewUserDTO newUserDTO) {
    var newUser = userClientService.insert(newUserDTO);
    var uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newUser.getId())
            .toUri();
    return ResponseEntity.created(uri).build();
  }

  @ApiOperation(value = "Check user email ")
  @PostMapping("/check/email")
  public ResponseEntity<UserEmailDto> checkUserEmail(@Valid @RequestBody CheckUserEmail checkUserEmail){
        var userEmail = userClientService.checkEmail(checkUserEmail);
        return ResponseEntity.ok(userEmail);
  }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update user")
    public ResponseEntity<ListUserDTO> update(@Valid @RequestBody UpdateUserDTO userDTO, @PathVariable Integer id) {
        var userUpdate = userClientService.update(id, userDTO);
        return ResponseEntity.ok(userUpdate);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete user")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userClientService.delete(id);
        return ResponseEntity.ok().build();
    }
}
