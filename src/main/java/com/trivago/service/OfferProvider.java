package com.trivago.service;

import com.trivago.model.DateRange;
import com.trivago.model.Offer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface OfferProvider {
  /**
   * Retrieve hotel stay prices from trivago advertisers. This is generally a very expensive
   * operation (network requests, database queries, etc.) and so should be called sparingly.
   *
   * @param advertiserId the ID of the advertiser to query for offers
   * @param hotelIds IDs of hotels that prices are needed for
   * @param dateRange stay dates that prices are needed for
   * @return a map {hotelID -> AdvertiserOffer} containing the advertiser's price for each requested
   *     hotel
   */
  Map<Integer, Offer> getOffersFromAdvertiser(
      int advertiserId, List<Integer> hotelIds, DateRange dateRange) throws IOException;
}
