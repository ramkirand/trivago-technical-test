package com.trivago.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Hotel {
  private final int id;
  private String name;
  private final int rating;
  private final int stars;

  public Hotel(int id, String name, int rating, int stars) {
    this.id = id;
    this.name = name;
    this.rating = rating;
    this.stars = stars;
  }
}
