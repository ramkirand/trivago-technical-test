package com.trivago.service;

import com.trivago.annotation.TrackExecutionTime;
import com.trivago.model.DateRange;
import com.trivago.model.Hotel;
import com.trivago.model.HotelWithOffers;
import com.trivago.model.Offer;
import com.trivago.util.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Component
public class HotelSearchEngineImpl implements HotelSearchEngine {
  private static final String SEARCH_CACHE = "searchCache";
  private final AtomicInteger queryCount = new AtomicInteger(0);
  private final DataProcessorService dataProcessorService;
  private final DataReaderService dataReaderService;

  @Autowired
  HotelSearchEngineImpl(
      DataProcessorService dataProcessorService, DataReaderService dataReaderService) {
    this.dataProcessorService = dataProcessorService;
    this.dataReaderService = dataReaderService;
  }

  @Override
  public void initialize() {
    dataReaderService.readCsvData();
    dataProcessorService.processCityData();
    dataProcessorService.processHotelData();
    dataProcessorService.processAdvertiserIdWithHotels();
  }

  @Override
  @TrackExecutionTime
  @Cacheable(value = SEARCH_CACHE, key = "#cityName")
  public List<HotelWithOffers> performSearch(
      String cityName, DateRange dateRange, OfferProvider offerProvider) {
    Integer cityId = Storage.cityIdMap.get(cityName);
    List<Hotel> allHotelsInCurrentCity =
        Storage.hotelInGivenCity.getOrDefault(cityId, new ArrayList<>());

    List<Integer> listHotelIdWithOffer = new ArrayList<>();
    for (Hotel hotel : allHotelsInCurrentCity) {
      Integer currentHotel = hotel.getId();
      if (!Storage.allHotelIdWithOfferSet.contains(currentHotel)) {
        continue;
      }

      if (!Storage.hotelIdToHotelMap.containsKey(currentHotel)) {
        continue;
      }
      listHotelIdWithOffer.add(hotel.getId());
    }

    Storage.advertiserToListHotelId.forEach(
        (k, v) -> {
          try {
            Map<Integer, Offer> hotelsToOfferFromThisAdvertiser =
                offerProvider.getOffersFromAdvertiser(k, listHotelIdWithOffer, dateRange);
            hotelsToOfferFromThisAdvertiser.forEach(
                (key, val) -> {
                  List<Offer> offers =
                      Storage.hotelIdToOffersList.getOrDefault(key, new ArrayList<>());
                  offers.add(val);
                  Storage.hotelIdToOffersList.put(key, offers);
                });

          } catch (IOException e) {
            log.debug(e.getMessage());
          }
        });

    List<HotelWithOffers> listHotelWithOffers = new ArrayList<>();
    listHotelIdWithOffer.forEach(
        l ->
            listHotelWithOffers.add(
                new HotelWithOffers(
                    Storage.hotelIdToHotelMap.get(l), Storage.hotelIdToOffersList.get(l))));
    queryCount.getAndIncrement();
    return listHotelWithOffers.stream().sorted().collect(Collectors.toList());
  }
  @Override
  public synchronized long totalQueries() {
    log.info("<<<<<<<<<<   Total Queries: " + queryCount.get());
    return queryCount.get();
  }
}
