package org.softengproj;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.softengproj.FileIO.DataReader;

public class ExportBoard {
    private static final Logger logger = LogManager.getLogger(DataReader.class);

    /**
     * the action of click yes button
     * 
     * @param actionEvent
     */
    public void yesaction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("choose the location for export");

        // set the default file name
        fileChooser.setInitialFileName("exported_data.txt");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // show the file choose
        File selectedFile = fileChooser.showSaveDialog(new Stage());

        if (selectedFile != null) {
            try {
                // get data of file that to export
                String dataToExport = "Your data here";

                // write data to the file
                try (FileWriter writer = new FileWriter(selectedFile)) {
                    writer.write(dataToExport);
                }

                System.out.println("successfully export：" + selectedFile.getAbsolutePath());
            } catch (IOException e) {
                logger.error("Something went wrong when exporting");
            }
        } else {
            System.out.println("no file export");
        }
    }

    /**
     * the action of click no button
     * 
     * @param actionEvent
     */
    public void noaction(ActionEvent actionEvent) {
        Platform.exit();
    }
}
