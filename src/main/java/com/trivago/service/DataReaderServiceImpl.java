package com.trivago.service;

import com.trivago.util.ReadCsvData;
import com.trivago.util.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DataReaderServiceImpl implements DataReaderService {
  @Value("${HOTEL_DATA}")
  private String hotelFile;

  @Value("${CITIES_DATA}")
  private String citiesFile;

  @Value("${HOTEL_ADVERTISER_DATA}")
  private String hotelAdvertiserFile;

  @Override
  public void readCsvData() {
    Storage.hotelData =
        ReadCsvData.readDataFromCsv(hotelFile).parallelStream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    log.info("hotelData:" + Storage.hotelData);
    Storage.cityData =
        ReadCsvData.readDataFromCsv(citiesFile).parallelStream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    log.info("cityData:" + Storage.cityData);
    Storage.hotelAdvertiserData =
        ReadCsvData.readDataFromCsv(hotelAdvertiserFile).parallelStream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
  }
}
