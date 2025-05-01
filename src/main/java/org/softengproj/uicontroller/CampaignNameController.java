package org.softengproj.uicontroller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.softengproj.App;
import org.softengproj.Controller;
import org.softengproj.MainController;
import org.softengproj.ui.CampaignUI;

public class CampaignNameController implements Initializable, Controller {

  @FXML
  private TextFlow impression;
  @FXML
  private TextFlow server;
  @FXML
  private TextFlow click;
  @FXML
  private TextFlow campaignName;
  private CampaignUI campaign;
  private MainController mainController;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }

  /**
   * Getting the impression textflow
   * 
   * @return impression textflow
   */
  public TextFlow getImpression() {
    return this.impression;
  }

  /**
   * Getting the server textflow
   * 
   * @return server textflow
   */
  public TextFlow getServer() {
    return this.server;
  }

  /**
   * Getting the click textflow
   * 
   * @return click textflow
   */
  public TextFlow getClick() {
    return this.click;
  }

  /**
   * Getting the campaign textflow
   * 
   * @return campaign textflow
   */
  public TextFlow getCampaign() {
    return this.campaignName;
  }

  /**
   * Showing the campaign
   */
  public void showCampaign() {
    if (!mainController.getLoaded()) {
      campaign.noCampaign();

    } else {
      campaign.showCampaign();
    }

  }

  /**
   * Setting the campaign ui
   * 
   * @param campaignUI campaign ui
   */
  public void setCampaignUI(CampaignUI campaignUI) {
    this.campaign = campaignUI;
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
}
