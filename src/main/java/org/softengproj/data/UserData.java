package org.softengproj.data;

public class UserData {

  private String user;
  private String role = "editor";

  /**
   * Setting the current user
   * 
   * @param user current user
   */
  public void setUser(String user) {
    this.user = user;
  }

  public String getRole() {
    return role;
  }
}
