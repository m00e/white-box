package de.m00e.whitebox.listeners;

import de.m00e.whitebox.components.TaskBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class LoadTaskListener implements EventHandler<ActionEvent> {

    private FileChooser fc;
    private FileChooser.ExtensionFilter extensionFilter;
    private File f;

    public LoadTaskListener() {
        fc = new FileChooser();
        extensionFilter = new FileChooser.ExtensionFilter("TASKS files (*.tasks)", "*.tasks");

        fc.getExtensionFilters().add(extensionFilter);
    }
    @Override
    public void handle(ActionEvent event) {
        f = fc.showOpenDialog(((Node) event.getTarget()).getScene().getWindow());
        if(f == null) return;
        try {
            TaskBox.loadTasks(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
