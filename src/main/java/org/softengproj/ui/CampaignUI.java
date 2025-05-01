package org.softengproj.ui;

import java.io.File;
import java.nio.file.Path;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.softengproj.App;
import org.softengproj.MainController;

public class CampaignUI {
  public TextFlow impression;
  public TextFlow server;
  public TextFlow click;
  public TextFlow campaign;
  File impressionfile;
  File clickfile;
  File serverfile;
  Path campaignName;

  public CampaignUI(TextFlow impression, TextFlow server, TextFlow click, TextFlow campaign, File impressionfile,
      File clickfile, File serverfile, Path campaignName) {
    this.impression = impression;
    this.server = server;
    this.click = click;
    this.impressionfile = impressionfile;
    this.clickfile = clickfile;
    this.serverfile = serverfile;
    this.campaign = campaign;
    this.campaignName = campaignName;
  }

  /**
   * Show there is no campaign loaded
   */
  public void noCampaign() {
    impression.getChildren().clear();
    impression.getChildren().add(new Text("No Campaign Loaded"));
  }

  /**
   * Show the file names on the campaign popup
   */
  public void showCampaign() {
    campaign.getChildren().clear();
    campaign.getChildren().add(new Text(campaignName.toString()));
    impression.getChildren().clear();
    impression.getChildren().add(new Text(impressionfile.getName()));
    server.getChildren().clear();
    server.getChildren().add(new Text(serverfile.getName()));
    click.getChildren().clear();
    click.getChildren().add(new Text(clickfile.getName()));
  }

}
