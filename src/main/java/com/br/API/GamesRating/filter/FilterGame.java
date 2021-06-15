package com.br.API.GamesRating.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilterGame {

  private String title;
  private String description;
  private String producer;
  private String platforms;
}
