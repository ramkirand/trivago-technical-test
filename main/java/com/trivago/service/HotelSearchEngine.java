package com.trivago.service;

import com.trivago.model.DateRange;
import com.trivago.model.HotelWithOffers;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface HotelSearchEngine {
  /**
   * An init function that reads all necessary data from disk, sets up necessary data structures,
   * etc.
   */
  void initialize() throws IOException;

  /**
   * The actual search engine functionality. Given the search context (the city as a String and the
   * date range), perform the search by using the OfferProvider interface.
   *
   * @param cityName name of the city to search for hotel offers in
   * @param dateRange check-in and check-out date for the desired offers
   * @param offerProvider for requesting offers from advertisers
   * @return a list of hotels in the city with offers provided by one or more advertisers for the
   *     given date range
   */
  List<HotelWithOffers> performSearch(
      String cityName, DateRange dateRange, OfferProvider offerProvider) throws IOException;

  /**
   * @return the total number of calls to {@link #performSearch} executed since the application
   *     started
   */
  long totalQueries();
}
