package org.softengproj.ui;

import java.io.File;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import org.softengproj.MetricTable;
import org.softengproj.Metrics;
import org.softengproj.FileIO.DataReader;
import org.softengproj.uicontroller.ChartsController;

public class ChartsUI {
  private static final Logger logger = LogManager.getLogger(ChartsUI.class);
  /**
   * The UI dropdown options for the metrics
   */
  private ComboBox<String> metricOptions;
  /**
   * The UI table column that contains all of the metrics
   */
  private TableColumn<MetricTable, String> metrics;
  /**
   * The UI table column that contains the computations of the metrics
   */
  private TableColumn<MetricTable, Integer> computation;
  /**
   * The directory chooser that the user uses to select the campaign directory
   */
  DirectoryChooser dirChooser = new DirectoryChooser();

  /**
   * The UI table that contains the metrics
   */
  public TableView<MetricTable> table;

  /**
   * The list which contains all of the metrics
   */
  private final ObservableList<MetricTable> listOfMetrics = FXCollections.observableArrayList();
  public ComboBox<String> time;
  private Boolean campaignLoaded = false;

  public ChartsUI(TableColumn<MetricTable, Integer> computation, TableColumn<MetricTable, String> metrics,
      ComboBox<String> metricOptions, TableView table, ComboBox time) {
    this.computation = computation;
    this.metricOptions = metricOptions;
    this.metrics = metrics;
    this.table = table;
    this.time = time;
  }

  /**
   * When the unload button is clicked, unload the campaign
   */
  public void unload(boolean loaded) {
    Alert alert;
    if (!loaded) {
      alert = new Alert(AlertType.WARNING, "No campaign was loaded so nothing was unloaded.", ButtonType.OK);
      alert.setTitle("Warning");
      alert.setHeaderText("No campaign loaded.");
    } else {
      alert = new Alert(AlertType.INFORMATION, "Campaign was successfully unloaded.", ButtonType.OK);
      alert.setTitle("Message");
      alert.setHeaderText("Campaign unloaded.");
    }
    listOfMetrics.clear();
    table.setItems(listOfMetrics);
    alert.showAndWait();
  }

  /**
   * When the load button is clicked, display the file loader where the user
   * inputs the campaign folder
   * 
   * @return a hash map with the campaign files
   */
  public HashMap<String, File> load() {
    Window stage = metricOptions.getScene().getWindow();
    dirChooser.setTitle("Load Data");
    File dir = dirChooser.showDialog(stage);
    File impressionFile = new File(dir + "/impression_log.csv");
    File clickFile = new File(dir + "/click_log.csv");
    File serverFile = new File(dir + "/server_log.csv");
    if (dir != null) {
      if (impressionFile.exists() && clickFile.exists() && serverFile.exists()) {
        HashMap<String, File> files = new HashMap<String, File>();
        files.put("impression", impressionFile);
        files.put("click", clickFile);
        files.put("server", serverFile);
        files.put("campaign", dir);
        return files;
      }
    } else {
      logger.error("Directory is null");
    }
    return null;
  }

  /**
   * Handle the incorrect files being in the campaign directory
   */
  public void handleFileError() {
    logger.error("Not all required files present in directory.");
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("An error has occured");
    alert.setContentText("Not all required files present in directory.");
    alert.showAndWait();
  }

  /**
   * Handle the correct files being in the file directory
   * 
   * @param dir the campaign directory
   */
  public void handleFileConfirmation(File dir) {
    logger.info("Successfully imported campaign folder, " + dir.toPath().getFileName() + ".");
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Message");
    alert.setHeaderText("Successfully loaded " + dir.toPath().getFileName() + ".");
    alert.setContentText("You can now view the campaign metrics.");
    alert.showAndWait();
  }

  /**
   * Gets all of the metrics using the Metrics class and adds them to the table
   */
  public void displayMetrics() {
    if (campaignLoaded) {
      listOfMetrics.clear();
      table.setItems(listOfMetrics);
      long impressions = Metrics.getNumImpressions();
      long clicks = Metrics.getNumClicks();
      long uniques = Metrics.getNumUniques();
      long bounces = Metrics.getNumBounces();
      long conversions = Metrics.getNumConversions();
      double cost = Metrics.getTotalCost();
      listOfMetrics.addAll(
          new MetricTable("Number of Impressions", impressions),
          new MetricTable("Number of Clicks", clicks),
          new MetricTable("Number of Uniques", uniques),
          new MetricTable("Number of Bounces", bounces),
          new MetricTable("Number of Conversions", conversions),
          new MetricTable("Total Cost", cost),
          new MetricTable("Click Through Rate", Metrics.getCTR(clicks, impressions)),
          new MetricTable("Cost per Acquisition", Metrics.getCPA(cost, conversions)),
          new MetricTable("Cost per Click", Metrics.getCPC(cost, clicks)),
          new MetricTable("Cost per Thousand Impressions", Metrics.getCPM(cost, impressions)),
          new MetricTable("Bounce Rate", Metrics.getBounceRate(bounces, clicks)));
      table.setItems(listOfMetrics);
    }
  }

  /**
   * Initializes the dropdowns and table columns
   */
  public void initializeItems() {
    metrics.setCellValueFactory(new PropertyValueFactory<>("metric"));
    computation.setCellValueFactory(new PropertyValueFactory<>("computation"));
    metricOptions.getItems().addAll(
        "Number of Impressions",
        "Number of Clicks",
        "Number of Uniques",
        "Number of Bounces",
        "Number of Conversions",
        "Total Cost",
        "CTR",
        "CPA",
        "CPC",
        "CPM",
        "Bounce Rate");
    metricOptions.setValue("Number of Impressions");
    time.getItems().addAll(
        "Days",
        "Hours",
        "Week");
    time.setValue("Days");
  }

  /**
   * Setting whether a campaign is loaded or not
   * 
   * @param bool whether a campaign is loaded or not
   */
  public void setCampaignLoaded(Boolean bool) {
    campaignLoaded = bool;
  }
}
