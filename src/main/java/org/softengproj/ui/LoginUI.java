package org.softengproj.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class LoginUI {
  /**
   * The alert that shows when no input is given
   * 
   * @param s alert text
   */
  public void alert(String s) {
    Alert alert = new Alert(AlertType.ERROR,
        s,
        ButtonType.OK);
    alert.showAndWait();

  }
}
