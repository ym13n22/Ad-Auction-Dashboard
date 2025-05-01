package org.softengproj.uicontroller;

import org.softengproj.Bounce;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.softengproj.MainController;
import org.softengproj.Controller;

public class BounceController implements Controller {

    /**
     * Combobox used as a dropdown to select how a bounce is defined
     */
    @FXML
    private ComboBox<String> bounceDropdown;

    /**
     * Label that changes depending on how a bounce is defined
     */
    @FXML
    private Label bounceDefLabel;

    /**
     * Text field where the user changes the values for the bounce
     */
    @FXML
    private TextField bounceInput;

    /**
     * The main controller
     */
    private MainController mainController;

    /**
     * Getting the bounce label
     * 
     * @return bounce label
     */
    public Label getBounceDefLabel() {
        return bounceDefLabel;
    }

    /**
     * Getting the bounce dropdown
     * 
     * @return bounce dropdown
     */
    public ComboBox<String> getBounceDropdown() {
        return bounceDropdown;
    }

    /**
     * Getting the bounce text field
     * 
     * @return bounce input
     */
    public TextField getBounceInput() {
        return bounceInput;
    }

    /**
     * Setting the main controller
     * 
     * @param controller the main controller
     */
    @Override
    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    /**
     * When the dropdown option is changed
     * 
     * @param event event that happened
     */
    public void comboAction(ActionEvent event) {
        Bounce.comboAction();
    }

    /**
     * When the text in the text field is changed
     * 
     * @param event event that happened
     */
    public void textChange(ActionEvent event) {
        Bounce.textChange();
    }

    /**
     * Setting up the default values of the UI elements
     */
    public void setup() {
        // Adding options to dropdown and setting initial text
        bounceDropdown.getItems().addAll("Time on Site", "Pages Visited");
        if (Bounce.isTimedBounce()) {
            bounceDropdown.setValue("Time on Site");
            bounceInput.setText(Bounce.getBounceTime());
        } else {
            bounceDropdown.setValue("Pages Visited");
            bounceInput.setText(Bounce.getBouncePages());
        }

        Bounce.setup(bounceDefLabel, bounceDropdown, bounceInput);
    }
}
