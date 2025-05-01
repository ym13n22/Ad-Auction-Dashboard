package org.softengproj.uicontroller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import org.softengproj.data.BoardData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.softengproj.Bounce;
import org.softengproj.Controller;
import org.softengproj.MainController;
import org.softengproj.MetricTable;
import org.softengproj.FileIO.DataReader;
import org.softengproj.ui.ChartsUI;

public class ChartsController implements Initializable, Controller {
  private static final Logger logger = LogManager.getLogger(ChartsController.class);
  public TableView table;
  @FXML
  public TableColumn<MetricTable, Integer> computation;
  @FXML
  public TableColumn<MetricTable, String> metrics;
  public Button load;
  public Button unload;
  public Button back;
  public VBox tablecontainer;
  public Button campaign;
  public ComboBox<String> metricOptions;
  public ComboBox time;
  public Bounce bounce;
  private final DirectoryChooser dirChooser = new DirectoryChooser();

  private final ObservableList<MetricTable> listofmetrics = FXCollections.observableArrayList(
      new MetricTable("Metric 1", 12),
      new MetricTable("Metric 2", 13));

  private MainController mainController;
  private ChartsUI ui;
  private BoardData data;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }

  public void initializeitems() {
    ui.initializeItems();
    Bounce.setChartsUI(ui);
  }

  /**
   * When the load button is pressed, get and set the campaign files accordingly
   *
   * @param event button click
   */
  public void load(ActionEvent event) {
    HashMap<String, File> files = ui.load();
    if (files != null) {
      File impression = files.get("impression");
      File click = files.get("click");
      File server = files.get("server");
      File campaign = files.get("campaign");
      if (this.readImpression(impression) && this.readClick(click) && this.readServer(server)) {
        mainController.setCampaignName(campaign.toPath());
        mainController.setClickFile(click);
        mainController.setImpressionFile(impression);
        mainController.setServerFile(server);
        ui.handleFileConfirmation(campaign);
        DataReader reader = new DataReader();
        reader.addCampaign(files.get("click").toString(), files.get("impression").toString(),
            files.get("server").toString());
        reader.addTableFromCSV(files.get("click").toString());
        ui.setCampaignLoaded(true);
        ui.displayMetrics();
      } else {
        ui.handleFileError();
      }
    } else {
      ui.handleFileError();
    }
  }

  private String readFile(File file, String firstline) {
    int lineNumber = 0;
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(file));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    try {
      String line = reader.readLine();
      while (lineNumber < 2) {
        if (lineNumber == 0) {
          if (!line.equals(firstline)) {
            return null;
          }
        } else if (lineNumber == 1) {
          return line;
        }
        lineNumber++;

      }
    } catch (IOException e) {
      logger.error("Something went wrong when reading the file");
    }
    return null;
  }

  private boolean readServer(File server) {
    String line = readFile(server, "Entry Date,ID,Exit Date,Pages Viewed,Conversion");
    return line != null;
  }

  private boolean readClick(File click) {
    String line = readFile(click, "Date,ID,Click Cost");
    return line != null;
  }

  private boolean readImpression(File impression) {
    String line = readFile(impression, "Date,ID,Gender,Age,Income,Context,Impression Cost");
    return line != null;
  }

  /**
   * When the unload button is pressed, remove all the campaign files
   *
   * @param event button click
   */
  public void unload(ActionEvent event) {
    if (mainController.getLoaded()) {
      mainController.setClickFile(null);
      mainController.setImpressionFile(null);
      mainController.setServerFile(null);
      mainController.setCampaignName(null);
      ui.unload(true);
    } else {
      ui.unload(false);
    }
    ui.setCampaignLoaded(false);
  }

  /**
   * When the back button is pressed go back to the menu
   *
   * @param event button press
   */
  public void back(ActionEvent event) {
    mainController.loadController("dashboard.fxml", "dashboard");
  }

  /**
   * When the campaign button is pressed show the campaign popup
   *
   * @param event button press
   */
  public void campaign(ActionEvent event) {
    mainController.openWindow("campaignname.fxml", "campaign");
  }

  /**
   * When the bounce button is pressed show the bounce popup
   *
   * @param event button press
   */
  public void changeBounce(ActionEvent event) {
    mainController.openWindow("bounce.fxml", "bounce");
  }

  /**
   * When the metrics button is pressed
   *
   * @param event button press
   */
  public void metrics(ActionEvent event) {

  }

  /**
   * When the filters button is pressed show the filters popup
   *
   * @param event button press
   */
  public void addFilters(ActionEvent event) {
    mainController.openWindow("filters.fxml", "filters");
  }

  /**
   * Setting the main controller
   *
   * @param controller main controller
   */
  @Override
  public void setMainController(MainController controller) {
    this.mainController = controller;
  }

  /**
   * Setting the charts ui
   *
   * @param ui charts ui
   */
  public void setChartsUI(ChartsUI ui) {
    this.ui = ui;
  }

  /**
   * Getting the computation table column
   *
   * @return computation table column
   */
  public TableColumn<MetricTable, Integer> getComputation() {
    return computation;
  }

  /**
   * Getting the metrics table column
   *
   * @return metrics table column
   */
  public TableColumn<MetricTable, String> getMetrics() {
    return metrics;
  }

  /**
   * Getting the table
   *
   * @return table
   */
  public TableView getTable() {
    return table;
  }

  /**
   * Getting the metric options
   *
   * @return metric options
   */
  public ComboBox<String> getMetricOptions() {
    return this.metricOptions;
  }

  /**
   * Getting the time dropdown
   *
   * @return time dropdown
   */
  public ComboBox getTime() {
    return time;
  }

  public void export(ActionEvent event) {
  }
}
