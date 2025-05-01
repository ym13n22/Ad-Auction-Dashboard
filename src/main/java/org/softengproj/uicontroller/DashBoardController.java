package org.softengproj.uicontroller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.softengproj.App;
import org.softengproj.Bounce;
import org.softengproj.Controller;
import org.softengproj.MainController;
import org.softengproj.ui.DashBoardUI;

public class DashBoardController implements Initializable, Controller {

  public Button dashboard;
  public Button logout;
  public Button roles;
  public Button loadcampaign;
  public VBox vbox;
  private MainController controller;
  private static final Logger logger = LogManager.getLogger(App.class);

  /**
   * Opening the dashboard
   *
   * @param event button press
   */
  private DashBoardUI ui;
  private String role;

  public void dashboard(ActionEvent event) {
    logger.info("opening dashboard");
    controller.loadController("dashboardcharts.fxml", "chart");
  }

  /**
   * Opening the manual
   *
   * @param event button press
   */
  public void manual(ActionEvent event) {
    logger.info("opening manual");
    controller.loadController("usermanual.fxml", "manual");
  }

  /**
   * Opening the summary
   *
   * @param event button press
   */
  public void summary(ActionEvent event) {
    controller.loadController("summary.fxml", "summary");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }

  /**
   * Logging out (opening login screen)
   *
   * @param event button press
   */
  public void logout(ActionEvent event) {
    controller.loadController("login.fxml", "login");
  }
  public void setUI(DashBoardUI ui){
    this.ui = ui;
  }
  public void setRole(String role){
    this.role = role;
  }
  public void showMenu(){
    if (role.equals("viewer")){
      ui.disableLoad();
      ui.disableRoles();
    }
    if (role.equals("editor")){
      ui.disableRoles();
    }
  }

  public Button getRoles(){
    return this.roles;
  }
  public Button getLoadcampaign(){
    return loadcampaign;
  }
  public VBox getVbox(){
    return this.vbox;
  }

  /**
   * Setting the main controller
   *
   * @param controller main controller
   */
  @Override
  public void setMainController(MainController controller) {
    this.controller = controller;
  }
}
