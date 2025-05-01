package org.softengproj.uicontroller;

import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.fxml.FXML;
import org.softengproj.App;
import org.softengproj.Controller;
import org.softengproj.MainController;
import org.softengproj.ui.LoginUI;

/**
 * Login page controller class
 */
public class LoginController implements Controller {

  public TextField username;
  public PasswordField password;
  @FXML
  private TextField textfield;

  private MainController controller;
  private LoginUI ui;

  /**
   * Setting the text field
   *
   * @param textfield text field
   */
  public void setTextfield(TextField textfield) {
    this.textfield = textfield;
  }

  /**
   * opens dashboard
   *
   * @param event user presses enter key event
   */
  @FXML
  protected void handleLogin(ActionEvent event) {
    // TODO: Link this to the login button
    if (Objects.equals(username.getText(), "") || Objects.equals(password.getText(), "")) {
      ui.alert("You have not entered the username or password");
      return;
    }
    controller.setRole();
    controller.setUser(username.getText());
    controller.loadController("dashboard.fxml", "dashboard");

  }

  /**
   * calls method to create dashboard
   *
   * @param event user pressed a key event
   */
  @FXML
  public void changedashboard(KeyEvent event) {
    if (event.getCode() != KeyCode.ENTER)
      return;
    handleLogin(null);
  }

  /**
   * Setting the login ui
   *
   * @param ui the login ui
   */
  public void setUI(LoginUI ui) {
    this.ui = ui;
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
