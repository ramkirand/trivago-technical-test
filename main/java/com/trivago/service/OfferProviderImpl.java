package com.trivago.service;

import com.trivago.constants.Constants;
import com.trivago.model.DateRange;
import com.trivago.model.Offer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OfferProviderImpl implements OfferProvider {

  private final HotelSearchEngine hotelSearchEngine;

  @Autowired
  OfferProviderImpl(HotelSearchEngine hotelSearchEngine) {
    this.hotelSearchEngine = hotelSearchEngine;
  }

  @Override
  public Map<Integer, Offer> getOffersFromAdvertiser(
      int advertiserId, List<Integer> hotelIds, DateRange dateRange) throws IOException {
    hotelSearchEngine.initialize();
    Map<Integer, Offer> offerMap = new HashMap<>();

    for (Integer hotelId : hotelIds) {
      Offer offer = new Offer(hotelId, Constants.PRICE_IN_EURO, Constants.CPC);
      offerMap.put(hotelId, offer);
    }
    log.info(" <<<<<<<<<<<<<<<<<<<<<< getOffersFromAdvertiser method end>>>>>>>>>>>>>>>>>>>>>>");
    return offerMap;
  }
}
