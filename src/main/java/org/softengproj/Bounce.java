package org.softengproj;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.softengproj.ui.ChartsUI;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Bounce {
    private static final Logger logger = LogManager.getLogger(Bounce.class);

    /**
     * Date format that is used to compare times user is on website
     */
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Variable that shows how a bounce is defined (timed or pages visited)
     */
    private static Boolean timedBounce = true;

    /**
     * Maximum time the user needs to be on the page for to still count as a bounce
     * Default bounce time is 30 seconds
     */
    private static long bounceTime = 30;

    /**
     * Maximum amount of pages the user needs to visit to still count as a bounce
     * Default bounce pages visited is 1
     */
    private static long bouncePages = 1;

    /**
     * Maximum time bounce time setting can be set to
     */
    private static final long MAX_TIME = 60;

    /**
     * Minumum time bounce time setting can be set to
     */
    private static final long MIN_TIME = 5;

    /**
     * The label that displays how a bounce is defined in the UI
     */
    private static Label bounceLabel;

    /**
     * The dropdown where the user chooses how a bounce is defined
     */
    private static ComboBox<String> bounceDropdown;

    /**
     * The text field where the user changes the bounce variable (time or pages
     * visited)
     */
    private static TextField bounceInput;

    /**
     * The charts ui
     */
    private static ChartsUI chartsUI;

    public static void setTimedBounce(Boolean timedBounce) {
        Bounce.timedBounce = timedBounce;
    }

    /**
     * Returns whether some user activity is registered as a bounce or not
     * 
     * @param startDate  the start date
     * @param endDate    the end date
     * @param conversion whether there was a conversion or not
     * @param pages      how many pages were visited
     * @return if activity is a bounce or not
     */
    public static Boolean isBounce(String startDate, String endDate, String conversion, String pages) {
        // If there is a conversion, it is not a bounce
        if (conversion.equals("Yes"))
            return false;
        // Checking if time spent on site is greater than bounceTime
        if (timedBounce) {
            // If there is not exit date, it is not a bounce
            if (endDate.equals("n/a"))
                return false;
            Date entryDate = null;
            Date exitDate = null;
            try {
                exitDate = format.parse(startDate);
                entryDate = format.parse(endDate);
            } catch (Exception e) {
                logger.error("Could not parse dates");
            }
            long diff = entryDate.getTime() - exitDate.getTime();
            long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
            return seconds <= bounceTime;
        }
        // Checking if pages viewed is greater than bouncePages
        return isBouncePages(pages);
    }

    public static Boolean isBouncePages(String pages) {
        return Long.parseLong(pages) <= bouncePages;
    }

    /**
     * When the combo box value is changed, the timedBounce variable is set
     * accordingly
     */
    public static void comboAction() {
        if (bounceDropdown.getValue() == "Time on Site") {
            timedBounce = true;
            bounceInput.setText(Long.toString(bounceTime));
        } else if (bounceDropdown.getValue() == "Pages Visited") {
            timedBounce = false;
            bounceInput.setText(Long.toString(bouncePages));
        }
        bounceDebugMessage();
        displayMetrics();
    }

    /**
     * When the text in the text field is changed, the values for bounce are also
     * changed
     */
    public static void textChange() {
        // Limit bounce time from MIN_TIME to MAX_TIME
        if (timedBounce) {
            if (Long.parseLong(bounceInput.getText()) < MIN_TIME) {
                bounceInput.setText(Long.toString(MIN_TIME));
                bounceTime = MIN_TIME;
            } else if (Long.parseLong(bounceInput.getText()) > MAX_TIME) {
                bounceInput.setText(Long.toString(MAX_TIME));
                bounceTime = MAX_TIME;
            } else {
                bounceTime = Long.parseLong(bounceInput.getText());
            }
        }
        // Don't limit bounce pages
        else {
            bouncePages = Long.parseLong(bounceInput.getText());
        }
        bounceDebugMessage();
        displayMetrics();
    }

    /**
     * Used to send a debug message which tells you how a bounce is defined and the
     * current setting for the bounce
     */
    private static void bounceDebugMessage() {
        if (timedBounce) {
            logger.info("Bounced registered using time on site.\nCurrent time on site setting: " + bounceTime + "\n");
        } else {
            logger.info("Bounce registered using pages visited.\nCurrent pages visited setting: " + bouncePages + "\n");
        }
    }

    /**
     * Setting up the bounce UI
     * 
     * @param label    the bounce label
     * @param dropdown the dropdown box
     * @param input    the input text field
     */
    public static void setup(Label label, ComboBox<String> dropdown, TextField input) {
        // Setting up variables
        bounceLabel = label;
        bounceDropdown = dropdown;
        bounceInput = input;

        bounceLabel.textProperty().bind(bounceDropdown.valueProperty());

        // Print initial settings
        logger.info("Min bounce time is set to: " + MIN_TIME);
        logger.info("Max bounce time is set to: " + MAX_TIME);
        logger.info("Bounce is currently registered as: " + bounceDropdown.getValue());
        logger.info("Settings: Time on Site: " + bounceTime + "; Pages Visited: " + bouncePages + "\n");
    }

    /**
     * Getting the charts ui where the metrics are displayed
     * 
     * @param ui charts ui
     */
    public static void setChartsUI(ChartsUI ui) {
        chartsUI = ui;
    }

    /**
     * Displaying the metrics
     */
    private static void displayMetrics() {
        chartsUI.displayMetrics();
    }

    /**
     * Checks if a bounce is registered using time on site or not
     */
    public static Boolean isTimedBounce() {
        return timedBounce;
    }

    /**
     * Getting bounce time
     * 
     * @return bounce time
     */
    public static String getBounceTime() {
        return Long.toString(bounceTime);
    }

    /**
     * Getting bounce pages
     * 
     * @return bounce pages
     */
    public static String getBouncePages() {
        return Long.toString(bouncePages);
    }
}
