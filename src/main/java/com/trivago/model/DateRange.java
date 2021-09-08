package com.trivago.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DateRange {
  private final int startDate;
  private final int endDate;

  public DateRange(int startDate, int endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
