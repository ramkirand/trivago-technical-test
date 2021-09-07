package com.trivago.service;

import com.trivago.model.DateRange;
import com.trivago.model.HotelWithOffers;
import com.trivago.model.Offer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HotelSearchEngineTest {

  @Autowired private HotelSearchEngine hotelSearchEngine;
  final Random random = new Random(42);
  private final OfferProvider dummyOfferProvider =
      (advertiserId, hotelIds, dateRange) -> {
        Map<Integer, Offer> result = new HashMap<>();
        for (Integer hotelId : hotelIds) {
          if (random.nextDouble() > 0.25) {
            result.put(
                hotelId, new Offer(advertiserId, random.nextInt(100) + 50, random.nextInt(10)));
          }
        }
        return result;
      };

  @Test
  public void shouldTestNoException() {
    assertDoesNotThrow(
        () -> {
          hotelSearchEngine.initialize();
          hotelSearchEngine.performSearch(
              "Berlin", new DateRange(20180214, 201802016), dummyOfferProvider);
        });
  }

  @Test
  public void shouldTestResultsReturned() throws IOException {
    hotelSearchEngine.initialize();
    List<HotelWithOffers> hotelsWithOffers =
        hotelSearchEngine.performSearch(
            "Berlin", new DateRange(20180214, 201802016), dummyOfferProvider);
    assertFalse(hotelsWithOffers.isEmpty(), "the result is not empty");
  }

  @Test
  public void shouldTestCorrectHotelsReturned() throws IOException {
    hotelSearchEngine.initialize();
    List<HotelWithOffers> hotelsWithOffers =
        hotelSearchEngine.performSearch(
            "Munich", new DateRange(20180214, 201802016), dummyOfferProvider);

    Set<Integer> hotelsInMunich =
        new HashSet<>(
            Arrays.asList(
                89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107,
                108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124,
                125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141,
                142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158,
                159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174,
                175));

    for (HotelWithOffers hotelWithOffers : hotelsWithOffers) {
      assertTrue(
          hotelsInMunich.contains(hotelWithOffers.getHotel().getId()),
          String.format(
              "returned wrong hotel, hotel %d is not in Munich",
              hotelWithOffers.getHotel().getId()));
    }
  }
  // For multiple threads
  @Test
  public void shouldTestMultiThreadPerformSearch() throws InterruptedException {
    int i = 0;
    AtomicReference<List<HotelWithOffers>> hotelsWithOffers = new AtomicReference<>();
    while (i < 10) {
      Thread first =
          new Thread(
              () -> {
                try {
                  hotelSearchEngine.initialize();
                  hotelsWithOffers.set(
                      hotelSearchEngine.performSearch(
                          "Berlin", new DateRange(20180214, 201802016), dummyOfferProvider));
                } catch (IOException e) {
                  e.printStackTrace();
                }
              });
      first.start();
      first.join();
      assertFalse(hotelsWithOffers.get().isEmpty(), "the result is not empty");
      i++;
    }
  }

  @Test
  public void shouldVerifyCorrectlyTotalQueries() {
      assertEquals(2, hotelSearchEngine.totalQueries());
  }
}
