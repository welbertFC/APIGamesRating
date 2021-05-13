package com.br.API.GamesRating.service;

import com.br.API.GamesRating.dto.ListUserDTO;
import com.br.API.GamesRating.dto.NewUserDTO;
import com.br.API.GamesRating.dto.UpdateUserDTO;
import com.br.API.GamesRating.exception.ObjectNotFoundException;
import com.br.API.GamesRating.exception.ObjectNotSaveException;
import com.br.API.GamesRating.model.Users;
import com.br.API.GamesRating.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users insert(NewUserDTO user) {
        validationUser(user);
        return userRepository.save(new Users(user));
    }

    public ListUserDTO findById(Integer id) {
        var user = userRepository.findById(id);
        user.orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));
        return new ListUserDTO(user);
    }

    public Users findByIdUser(Integer id) {
        var user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));
    }

    public Page<ListUserDTO> findAll() {
        var user = userRepository.findAll();
        var userList = user.stream().map(obj -> new ListUserDTO(obj))
                .sorted(Comparator.comparing(ListUserDTO::getName))
                .collect(Collectors.toList());
        return new PageImpl<>(userList);
    }

    public ListUserDTO update(Integer id, UpdateUserDTO updateUserDTO) {
        var user = findById(id);
        ValidationUpdateUser(updateUserDTO, user);
        var userUpdate = userRepository.save(new Users(id, updateUserDTO, user));
        return new ListUserDTO(userUpdate);
    }

    public void delete(Integer id) {
        var user = userRepository.findById(id);
        var userDelete = user.orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));
        userRepository.delete(userDelete);
    }

    public void validationUser(NewUserDTO user) {
        var userEmail = userRepository.findByEmail(user.getEmail());
        var userNickname = userRepository.findByNickName(user.getNickName());

        if (userEmail != null) {
            throw new ObjectNotSaveException("Email já cadastrado");
        }

        if (userNickname != null) {
            throw new ObjectNotSaveException("NickName já cadastrado");
        }

        if (user.getBirthDate().until(LocalDate.now(), ChronoUnit.YEARS) < 18) {
            throw new ObjectNotSaveException("Usuário deve possuir mais de 18 anos");
        }
    }

    public void ValidationUpdateUser(UpdateUserDTO userDTO, ListUserDTO listUserDTO) {
        var userNickname = userRepository.findByNickName(userDTO.getNickName());

        if (userNickname != null) {
            if (!listUserDTO.getNickName().equals(userNickname.getNickName())) {
                throw new ObjectNotSaveException("NickName já cadastrado");
            }
        }

        if (userDTO.getBirthDate().until(LocalDate.now(), ChronoUnit.YEARS) < 18) {
            throw new ObjectNotSaveException("Usuário deve possuir mais de 18 anos");
        }
    }


}
