package org.softengproj;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.softengproj.data.BoardData;
import org.softengproj.data.UserData;
import org.softengproj.ui.CampaignUI;
import org.softengproj.ui.ChartsUI;
import org.softengproj.ui.DashBoardUI;
import org.softengproj.ui.LoginUI;
import org.softengproj.uicontroller.BounceController;
import org.softengproj.uicontroller.CampaignNameController;
import org.softengproj.uicontroller.ChartsController;
import org.softengproj.uicontroller.DashBoardController;
import org.softengproj.uicontroller.FiltersController;
import org.softengproj.uicontroller.LoginController;

public class MainController {
  private LoginController logincontroller;
  private DashBoardController dashBoardController;
  private ChartsController chartsController;
  private CampaignNameController campaignNameController;
  private FiltersController filtersController;
  private BounceController bounceController;
  private Stage stage;
  private UserData userData;
  File impressionfile;
  File clickfile;
  File serverfile;
  Path campaignName;
  private BoardData data;
  private String role;

  public MainController(Stage stage) {
    this.stage = stage;
  }

  /**
   * Loading the controller
   * 
   * @param filename fxml file name
   * @param name     controller name
   */
  public void loadController(String filename, String name) {
    FXMLLoader loader = this.loader(filename);
    Parent root = this.findroot(loader);
    Scene scene = new Scene(root);
    stage.setScene(scene);
    switch (name) {
      case "dashboard" -> createBoardController(loader);
      case "chart" -> createChartController(loader);
      case "login" -> createLoginController(loader);
    }

  }

  public void openWindow(String filename, String name) {
    FXMLLoader loader = this.loader(filename);
    Parent root = this.findroot(loader);
    Scene scene = new Scene(root);
    switch (name) {
      case "campaign" -> createCampaignController(loader);
      case "filters" -> createFilterController(loader);
      case "bounce" -> createBounceController(loader);
    }
    Stage inputStage = new Stage();
    inputStage.initOwner(stage);
    inputStage.setScene(scene);
    inputStage.showAndWait();

  }

  private FXMLLoader loader(String filename) {
    return new FXMLLoader(getClass().getResource(filename));

  }

  private Parent findroot(FXMLLoader loader) {
    Parent root = null;
    try {
      root = loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return root;
  }

  private void createBounceController(FXMLLoader loader) {
    bounceController = loader.getController();
    bounceController.setMainController(this);
    bounceController.setup();
  }

  private void createFilterController(FXMLLoader loader) {
    filtersController = loader.getController();
    filtersController.setMainController(this);
  }

  private void createCampaignController(FXMLLoader loader) {
    campaignNameController = loader.getController();
    campaignNameController.setMainController(this);
    TextFlow campaign = campaignNameController.getCampaign();
    TextFlow impression = campaignNameController.getImpression();
    TextFlow server = campaignNameController.getServer();
    TextFlow click = campaignNameController.getClick();
    CampaignUI campaignUI = new CampaignUI(impression, server, click, campaign, impressionfile, clickfile, serverfile,
        campaignName);
    campaignNameController.setCampaignUI(campaignUI);
    campaignNameController.showCampaign();
  }

  private void createLoginController(FXMLLoader loader) {
    logincontroller = loader.getController();
    LoginUI loginUI = new LoginUI();
    logincontroller.setMainController(this);
    logincontroller.setUI(loginUI);
  }

  private void createChartController(FXMLLoader loader) {
    chartsController = loader.getController();
    chartsController.setMainController(this);
    TableColumn<MetricTable, Integer> computation = chartsController.getComputation();
    TableColumn<MetricTable, String> metrics = chartsController.getMetrics();
    ComboBox<String> metricOptions = chartsController.getMetricOptions();
    TableView table = chartsController.getTable();
    ComboBox time = chartsController.getTime();
    ChartsUI chartsUI = new ChartsUI(computation, metrics, metricOptions, table, time);
    chartsController.setChartsUI(chartsUI);
    chartsController.initializeitems();

  }

  private void createBoardController(FXMLLoader loader) {
    dashBoardController = loader.getController();
    VBox vbox = dashBoardController.getVbox();
    Button roles = dashBoardController.getRoles();
    Button load = dashBoardController.getLoadcampaign();
    DashBoardUI ui = new DashBoardUI(vbox, roles, load);
    dashBoardController.setMainController(this);
    dashBoardController.setUI(ui);
    dashBoardController.setRole(this.role);
    dashBoardController.showMenu();

  }

  public void setData(BoardData boardData) {
    this.data = boardData;
  }

  public void setClickFile(File click) {
    this.clickfile = click;
    data.setClickFile(click);
  }

  public void setServerFile(File server) {
    this.serverfile = server;
    data.setServerFile(server);
  }

  public void setImpressionFile(File impression) {
    this.impressionfile = impression;
    data.setImpressionFile(impression);
  }

  public void setCampaignName(Path name) {
    this.campaignName = name;
    data.setCampaignName(name);
  }

  public void setUserData(UserData userData) {
    this.userData = userData;
  }

  public void setUser(String user) {
    userData.setUser(user);
  }

  public void setRole() {
    this.role = userData.getRole();
  }

  public boolean getLoaded() {
    return data.getLoaded();
  }
}
