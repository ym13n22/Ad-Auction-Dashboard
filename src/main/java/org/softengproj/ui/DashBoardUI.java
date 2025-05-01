package org.softengproj.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class DashBoardUI {
  public Button roles;
  public Button loadcampaign;
  public VBox vbox;

  public DashBoardUI(VBox vbox, Button roles, Button load) {
    this.vbox = vbox;
    this.roles = roles;
    this.loadcampaign = load;
  }

  /**
   * Disabling the roles
   */
  public void disableRoles() {
    roles.setDisable(true);
    vbox.getChildren().remove(roles);
  }

  /**
   * Disabling loaded campaign
   */
  public void disableLoad() {
    loadcampaign.setDisable(true);
    vbox.getChildren().remove(loadcampaign);
  }

}
