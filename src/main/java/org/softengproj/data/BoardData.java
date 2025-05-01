package org.softengproj.data;

import java.io.File;
import java.nio.file.Path;

public class BoardData {

  private File impressionFile;
  private File clickFile;
  private File serverFile;
  private Path campaignName;

  /**
   * Setting the file containing impression data
   * 
   * @param impressionfile impression file
   */
  public void setImpressionFile(File impressionfile) {
    this.impressionFile = impressionfile;
  }

  /**
   * Setting the file containing click data
   * 
   * @param click_file click file
   */
  public void setClickFile(File click_file) {
    this.clickFile = click_file;
  }

  /**
   * Setting the file containing server data
   * 
   * @param server_file server file
   */
  public void setServerFile(File server_file) {
    this.serverFile = server_file;
  }

  /**
   * Setting the campaign directory file
   * 
   * @param file campaign directory
   */
  public void setCampaignName(Path file) {
    this.campaignName = file;
  }

  /**
   * Getting whether a campaign is loaded or not
   * 
   * @return if a campaign is currently loaded
   */
  public boolean getLoaded() {
    return campaignName != null;
  }
}
