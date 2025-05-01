package org.softengproj;

import static java.lang.Double.parseDouble;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.softengproj.FileIO.DataReader;

public class Metrics {
  private static final Logger logger = LogManager.getLogger(DataReader.class);
  /**
   * Create the format for doubles
   */
  private static final DecimalFormat df = new DecimalFormat("#.######");

  /**
   * The data reader that queries the database
   */
  private static DataReader dataReader = new DataReader();

  /*
   * Runs when class is loaded
   * Sets the format rounding to standard >=.5 -> 1, <.5 -> 0
   */
  static {
    df.setRoundingMode(RoundingMode.HALF_UP);
  }

  /**
   * round a double to 6 decimal places
   *
   * @param input the double to be rounded
   * @return the rounded double
   */
  private static double round(double input) {
    return parseDouble(df.format(input));
  }

  /**
   * get the number of impressions
   * 
   * @return number of impressions
   */
  public static long getNumImpressions() {
    return Long.parseLong(query("SELECT COUNT(ID) FROM impression_log;")) - 1;
  }

  /**
   * get the number of clicks
   * 
   * @return number of clicks
   */
  public static long getNumClicks() {
    return Long.parseLong(query("SELECT COUNT(ID) FROM click_log;")) - 1;
  }

  /**
   * get the number of unique clicks
   * 
   * @return number of unique clicks
   */
  public static long getNumUniques() {
    return Long.parseLong(query("SELECT COUNT(ID) FROM (SELECT DISTINCT ID FROM click_log);")) - 1;
  }

  /**
   * get the number of bounces
   * 
   * @return number of bounces
   */
  public static long getNumBounces() {
    long counter = 0;
    try {
      ResultSet result = dataReader.query("SELECT * FROM server_log;");
      result.next();
      while (result.next()) {
        if (Bounce.isBounce(result.getString(1), result.getString(3), result.getString(5), result.getString(4)))
          counter++;
      }
    } catch (Exception e) {
      logger.error("Something went wrong querying the number of bounces");
    }
    return counter;
  }

  /**
   * get the number of conversions
   * 
   * @return number of conversions
   */
  public static long getNumConversions() {
    return Long.parseLong(query("SELECT COUNT(Conversion) FROM server_log WHERE Conversion=\"Yes\";"));
  }

  /**
   * calculate the total cost of clicks
   *
   * @return total cost of clicks
   */
  public static double getTotalCost() {
    return round(Double.parseDouble(query("SELECT SUM(Click) FROM click_log;")));
  }

  /**
   * calculate the click-through-rate
   *
   * @param numClicks      number of clicks
   * @param numImpressions number of impressions
   * @return ctr
   */
  public static double getCTR(long numClicks, long numImpressions) {
    return round((double) numClicks / numImpressions);
  }

  /**
   * calculate the cost-per-acquisition
   *
   * @param totalCost        total cost
   * @param totalConversions total number of conversions
   * @return cpa
   */
  public static double getCPA(double totalCost, long totalConversions) {
    return round(totalCost / totalConversions);
  }

  /**
   * calculate the cost per click
   *
   * @param totalCost total cost
   * @param numClicks number of clicks
   * @return cpc
   */
  public static double getCPC(double totalCost, long numClicks) {
    return round(totalCost / numClicks);
  }

  /**
   * calculate the cost-per-thousand impressions
   *
   * @param totalCost      total cost
   * @param numImpressions number of impressions
   * @return cpm
   */
  public static double getCPM(double totalCost, long numImpressions) {
    return round(totalCost / numImpressions * 1000);
  }

  /**
   * calculate the bounce rate
   *
   * @param numBounces number of bounces
   * @param numClicks  number of clicks
   * @return bounce rate
   */
  public static double getBounceRate(long numBounces, long numClicks) {
    return round((double) numBounces / numClicks);
  }

  /**
   * Queries the database using the DataReader class
   * 
   * @param code the query to be used
   * @return the output from the query
   */
  private static String query(String code) {
    ResultSet result = dataReader.query(code);
    try {
      result.next();
      return result.getString(1);
    } catch (Exception e) {
      logger.error("Something went wrong when querying the database");
    }
    return "-1";
  }
}