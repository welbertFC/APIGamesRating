package com.br.API.GamesRating.security;

import com.br.API.GamesRating.model.enums.UserProfile;
import com.br.API.GamesRating.util.DateFormate;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserSS implements UserDetails {

  private Integer id;
  private String name;
  private String nickname;
  private String email;
  private String birthDate;
  private String dateCreated;
  private String urlImage;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  @Autowired
  private DateFormate dateFormate;


  public UserSS(
      Integer id,
      String name,
      String nickname,
      String email,
      String birthDate,
      String dateCreated,
      String urlImage,
      String password) {
    this.id = id;
    this.name = name;
    this.nickname = nickname;
    this.birthDate = birthDate;
    this.dateCreated = dateCreated;
    this.urlImage = urlImage;
    this.email = email;
    this.password = password;
  }

  public UserSS() {}

  @JsonIgnore
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Integer getId() {
    return id;
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return password;
  }


  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public boolean hasRole(UserProfile profile) {
    return getAuthorities().contains(new SimpleGrantedAuthority(profile.getDescription()));
  }

}
