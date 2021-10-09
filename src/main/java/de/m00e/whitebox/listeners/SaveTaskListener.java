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
    private File f;

    // Decides if the task list is saved into a new file despite a current working file existing.
    private boolean saveIntoNew;

    public SaveTaskListener(boolean saveIntoNew) {
        this.saveIntoNew = saveIntoNew;

        fc = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TASKS files (*.tasks)", "*.tasks");

    }

    @Override
    public void handle(ActionEvent event) {
        try {
            /* First look if there is already a file the user works on.
             If there is one, save it in there. */
            // BUG: If such a file exists, then it is not possible (in the same session) to save to tasks into another file
            //TODO: Solve with Clear Button??
            if(saveIntoNew || TaskBox.getRecentFile() == null) {
                f = fc.showSaveDialog(((Node) event.getTarget()).getScene().getWindow());
                if(f == null) return;
            } else {
                f = TaskBox.getRecentFile();
            }

            TaskBox.saveTasks(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
