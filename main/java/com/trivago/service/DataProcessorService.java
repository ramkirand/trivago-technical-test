package com.trivago.service;

import org.springframework.stereotype.Service;

@Service
public interface DataProcessorService {
  void processCityData();

  void processHotelData();

  void processAdvertiserIdWithHotels();
}
