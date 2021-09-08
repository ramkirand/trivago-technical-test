package com.trivago.util;

import com.trivago.model.Hotel;
import com.trivago.model.Offer;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

@UtilityClass
public class Storage {
  public List<String> hotelData, cityData, hotelAdvertiserData = new CopyOnWriteArrayList<>();
  public Map<String, Integer> cityIdMap = new ConcurrentHashMap<>();
  public Map<Integer, List<Hotel>> hotelInGivenCity = new ConcurrentHashMap<>();
  public Set<Integer> allHotelIdWithOfferSet = new ConcurrentSkipListSet<>();
  public Map<Integer, Hotel> cityIdHotelMap = new ConcurrentHashMap<>();
  public Map<Integer, Hotel> hotelIdToHotelMap = new ConcurrentHashMap<>();
  public Map<Integer, List<Integer>> advertiserToListHotelId = new ConcurrentHashMap<>();
  public Map<Integer, List<Offer>> hotelIdToOffersList = new HashMap<>();
}
