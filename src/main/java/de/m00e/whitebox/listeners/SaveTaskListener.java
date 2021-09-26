package de.m00e.whitebox.listeners;

import de.m00e.whitebox.components.TaskBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class SaveTaskListener implements EventHandler<ActionEvent> {

    private FileChooser fc;
    private FileChooser.ExtensionFilter extensionFilter;
    private File f;

    public SaveTaskListener() {
        fc = new FileChooser();
        extensionFilter = new FileChooser.ExtensionFilter("TASKS files (*.tasks)", "*.tasks");

    }
    @Override
    public void handle(ActionEvent event) {
        f = fc.showSaveDialog(((Node) event.getTarget()).getScene().getWindow());
        try {
            TaskBox.saveTasks(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
