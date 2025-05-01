package org.softengproj;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * testing the metrics calculations
 */
public class MetricsTest {
  // will add some more tests in the future

  @Test
  public void getNumImpressions() {
    long result = Metrics.getNumImpressions();
    long expectedResult = 486104;
    assertEquals(
        "Num impressions calculation failed.",
        result,
        expectedResult);
  }

  @Test
  public void getNumClicks() {
    long result = Metrics.getNumClicks();
    long expectedResult = 23923;
    assertEquals(
        "Num clicks calculation failed.",
        result,
        expectedResult);
  }

  @Test
  public void getNumUniques() {
    long result = Metrics.getNumUniques();
    long expectedResult = 23806;
    assertEquals(
        "Num uniques calculation failed.",
        result,
        expectedResult);
  }

  @Test
  public void getNumBounces() {
    long result = Metrics.getNumBounces();
    long expectedResult = 6168;
    assertEquals(
        "Num bounces calculation failed.",
        result,
        expectedResult);
  }

  @Test
  public void getNumConversions() {
    long result = Metrics.getNumConversions();
    long expectedResult = 2026;
    assertEquals(
        "Num conversions calculation failed.",
        result,
        expectedResult);
  }

  @Test
  public void getTotalCost() {
    double result = Metrics.getTotalCost();
    double expectedResult = 117610.865725;
    assertEquals(
        "Total cost calculation failed.",
        result,
        expectedResult,
        0.0);
  }

  @Test
  public void getCTR() {
    double result = Metrics.getCTR(Metrics.getNumClicks(), Metrics.getNumImpressions());
    double expectedResult = 0.049214;
    assertEquals(
        "CTR calculation failed.",
        result,
        expectedResult,
        0.0);
  }

  @Test
  public void getCPA() {
    double result = Metrics.getCPA(Metrics.getTotalCost(), Metrics.getNumConversions());
    double expectedResult = 58.050773;
    assertEquals(
        "CPA calculation failed.",
        result,
        expectedResult,
        0.0);
  }

  @Test
  public void getCPC() {
    double result = Metrics.getCPC(Metrics.getTotalCost(), Metrics.getNumClicks());
    double expectedResult = 4.916226;
    assertEquals(
        "CPC calculation failed.",
        result,
        expectedResult,
        0.0);
  }

  @Test
  public void getCPM() {
    double result = Metrics.getCPM(Metrics.getTotalCost(), Metrics.getNumImpressions());
    double expectedResult = 241.945892;
    assertEquals(
        "CPM calculation failed.",
        result,
        expectedResult,
        0.0);
  }

  @Test
  public void getBounceRate() {
    double result = Metrics.getBounceRate(Metrics.getNumBounces(), Metrics.getNumClicks());
    double expectedResult = 0.257827;
    assertEquals(
        "Bounce rate calculation failed.",
        result,
        expectedResult,
        0.0);
  }
}
