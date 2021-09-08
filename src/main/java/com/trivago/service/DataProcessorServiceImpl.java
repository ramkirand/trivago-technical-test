package com.trivago.service;

import com.trivago.model.Hotel;
import com.trivago.util.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Component
public class DataProcessorServiceImpl implements DataProcessorService {
  @Override
  public void processCityData() {
    log.info("processCityData >>>>>>>>>>>");
    Storage.cityIdMap = new LinkedHashMap<>();
    for (int i = 2; i < Storage.cityData.size(); i = i + 2) {
      Storage.cityIdMap.put(
          Storage.cityData.get(i + 1), Integer.parseInt(Storage.cityData.get(i).trim()));
    }
  }

  @Override
  public void processHotelData() {
    for (int i = 7; i < Storage.hotelData.size(); i = i + 7) {
      int id = Integer.parseInt(Storage.hotelData.get(i).trim());
      Hotel hotel =
          new Hotel(
              id,
              Storage.hotelData.get(i + 4).trim(),
              Integer.parseInt(Storage.hotelData.get(i + 5).trim()),
              Integer.parseInt(Storage.hotelData.get(i + 6).trim()));

      Integer key = Integer.parseInt(Storage.hotelData.get(i + 1).trim());
      List<Hotel> hotels = Storage.hotelInGivenCity.getOrDefault(key, new ArrayList<>());
      hotels.add(hotel);
      Storage.hotelIdToHotelMap.put(hotel.getId(), hotel);
      Storage.hotelInGivenCity.put(key, hotels);
      Storage.cityIdHotelMap.put(Integer.parseInt(Storage.hotelData.get(i + 1).trim()), hotel);
    }
  }

  @Override
  public void processAdvertiserIdWithHotels() {
    for (int i = 2; i < Storage.hotelAdvertiserData.size(); i = i + 2) {
      Integer currentAdvertiserId = Integer.parseInt(Storage.hotelAdvertiserData.get(i).trim());
      List<Integer> currentHotelIds =
          Storage.advertiserToListHotelId.getOrDefault(currentAdvertiserId, new ArrayList<>());
      Integer currentHotelId = Integer.parseInt(Storage.hotelAdvertiserData.get(i + 1).trim());
      currentHotelIds.add(currentHotelId);
      Storage.allHotelIdWithOfferSet.add(currentHotelId);
      Storage.advertiserToListHotelId.put(currentAdvertiserId, currentHotelIds);
    }
    log.info("<<<<<<<<<<<< advertiserToListHotelId" + Storage.advertiserToListHotelId);
  }
}
