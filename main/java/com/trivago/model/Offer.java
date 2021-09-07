package com.trivago.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Offer {
  private final int advertiserId;
  private final int priceInEuro;
  private final int cpc;

  public Offer(int advertiserId, int priceInEuro, int cpc) {
    this.advertiserId = advertiserId;
    this.priceInEuro = priceInEuro;
    this.cpc = cpc;
  }
}
