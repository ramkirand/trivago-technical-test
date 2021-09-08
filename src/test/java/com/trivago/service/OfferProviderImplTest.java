package com.trivago.service;

import com.trivago.model.DateRange;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {OfferProviderImpl.class})
@ExtendWith(SpringExtension.class)
public class OfferProviderImplTest {
  @MockBean private HotelSearchEngine hotelSearchEngine;

  @Autowired private OfferProviderImpl offerProviderImpl;

  @Test
  public void shouldTestGetOffersFromAdvertiser() throws IOException {
    doNothing().when(this.hotelSearchEngine).initialize();
    ArrayList<Integer> hotelIds = new ArrayList<>();
    assertTrue(
        this.offerProviderImpl
            .getOffersFromAdvertiser(123, hotelIds, new DateRange(1, 3))
            .isEmpty());
    verify(this.hotelSearchEngine).initialize();
  }
}
