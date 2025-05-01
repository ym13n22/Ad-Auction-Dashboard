package org.softengproj.uicontroller;

import org.softengproj.App;
import org.softengproj.Controller;
import org.softengproj.MainController;

public class FiltersController implements Controller {
  private MainController controller;

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
