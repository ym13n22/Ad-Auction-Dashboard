package org.softengproj;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.softengproj.data.BoardData;
import org.softengproj.data.UserData;

/**
 * Class to create the entry point to the application
 */

public class App extends Application {
  private static final Logger logger = LogManager.getLogger(App.class);

  private Stage stage;

  private MainController controller;

  /**
   * entry point for the application
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    launch();
  }

  /**
   * the start point for javafx
   *
   * @param stage the entry stage
   */
  @Override
  public void start(Stage stage) {
    this.stage = stage;
    controller = new MainController(stage);
    launchMainWindow();

  }

  /**
   * launches login page through controller
   */
  public void launchLoginTest() {
    controller.loadController("login.fxml", "login");
  }

  /**
   * creates the main window
   */
  private void launchMainWindow() {
    logger.info("Launching client window");
    stage.setTitle("Ad Auction Dashboard");
    stage.setOnCloseRequest(ev -> {
      shutdown();
    });
    controller.setData(new BoardData());
    controller.setUserData(new UserData());
    launchLoginTest();
    stage.show();
  }

  /**
   * Exits the program
   */
  public void shutdown() {
    System.exit(0);
  }
}
