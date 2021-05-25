package com.br.API.GamesRating.service;

import com.br.API.GamesRating.dto.ListUserDTO;
import com.br.API.GamesRating.dto.NewUserDTO;
import com.br.API.GamesRating.dto.UpdateUserDTO;
import com.br.API.GamesRating.exception.AuthorizationException;
import com.br.API.GamesRating.exception.ObjectNotFoundException;
import com.br.API.GamesRating.exception.ObjectNotSaveException;
import com.br.API.GamesRating.model.UserClient;
import com.br.API.GamesRating.model.enums.UserProfile;
import com.br.API.GamesRating.repository.UserRepository;
import com.br.API.GamesRating.security.SecuritySetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class UserClientService {

  @Autowired
  private SecuritySetting passwordEncoder;


  @Autowired private UserRepository userRepository;

  public UserClient insert(NewUserDTO user) {
    validationUser(user);
    var password = passwordEncoder.bCryptPasswordEncoder().encode(user.getPassword());
    return userRepository.save(new UserClient(user, password));

  }

  public ListUserDTO findById(Integer id) {
    var userSS = UserService.authenticated();
    if (userSS == null || !userSS.hasRole(UserProfile.ADMIN) && !id.equals(userSS.getId())){
      throw new AuthorizationException("Acesso negado");
    }

    var user = userRepository.findById(id);
    user.orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));
    return new ListUserDTO(user);
  }

  public UserClient findByIdUser(Integer id) {
    var user = userRepository.findById(id);
    return user.orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));
  }

  public Page<ListUserDTO> findAll() {
    var user = userRepository.findAll();
    var userList =
        user.stream()
            .map(obj -> new ListUserDTO(obj))
            .sorted(Comparator.comparing(ListUserDTO::getName))
            .collect(Collectors.toList());
    return new PageImpl<>(userList);
  }

  public ListUserDTO update(Integer id, UpdateUserDTO updateUserDTO) {
    var user = findById(id);
    ValidationUpdateUser(updateUserDTO, user);
    var userUpdate = userRepository.save(new UserClient(id, updateUserDTO, user));
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

    if(user.getUrlImage() == null || user.getUrlImage().isBlank()){
      user.setUrlImage("https://uploads-ssl.webflow.com/6030077fdbd53858ff7c4765/603c1ac00b9e8a080528b4ae_SalonBrillareGenericProfileAvi.jpg");
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
