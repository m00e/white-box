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
        new FileChooser.ExtensionFilter("TASKS files (*.tasks)", "*.tasks");
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            File fileWithExtension = null;
            /* First look if there is already a file the user works on.
             If there is one, save it in there. If 'save-as' button is pressed, change in new file regardless. */
            if(saveIntoNew || TaskBox.getRecentFile() == null) {
                f = fc.showSaveDialog(((Node) event.getTarget()).getScene().getWindow());
                // TODO: Fix bug; file can't be named after directory in the same path
                if(f == null) // Nothing was selected.
                    return;

                // Automatically save file as '.tasks'-file.
                if(!f.getAbsolutePath().toLowerCase().endsWith(".tasks")) {
                    fileWithExtension = new File(f.getAbsolutePath()+".tasks");
                }
                TaskBox.saveTasks(fileWithExtension);
            } else {
                f = TaskBox.getRecentFile();
                TaskBox.saveTasks(f);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
