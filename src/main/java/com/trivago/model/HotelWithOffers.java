package com.trivago.model;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class HotelWithOffers {
  private final Hotel hotel;
  /** A list of concrete advertiser offers for this hotel */
  private final List<Offer> offers;

  public HotelWithOffers(Hotel hotel, List<Offer> offers) {
    this.hotel = hotel;
    this.offers = offers;
  }

  public Hotel getHotel() {
    return hotel;
  }
}
