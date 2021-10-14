package de.m00e.whitebox.components;

import de.m00e.whitebox.WhiteBoxApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class Task extends BorderPane {

    private Label taskNrLbl;
    private CheckBox checkBox;
    private Button editBtn, abortBtn, delBtn;
    private TextField textField;

    private ImagePane editIcon, abortIcon, delIcon;

    private FlowPane flowPane; // Main menu of each task

    private int taskNr;
    private Status status;

    public Task(int taskNr) {
        this.taskNr = taskNr;
        status = Status.RUNNING;

        setupComponents();
        addListeners();

        flowPane.getChildren().addAll(checkBox, taskNrLbl, editBtn, abortBtn, delBtn);
        setTop(flowPane);
        setCenter(textField);
    }

    public Task(String info[]) {
        this.taskNr = Integer.parseInt(info[0]);
        this.status = Status.valueOf(info[1]);

        setupComponents();
        addListeners();

        if(info.length == 3)
            textField.setText(info[2]);
        else
            textField.setText("");

        // Done or aborted tasks must be marked as end immediately, otherwise they are still considered running
        switch(status) {
            case DONE:
                checkBox.setSelected(true);
                endTask(true);
                break;
            case ABORTED:
                endTask(false);
                break;
            case RUNNING:
                // Task text field should be disabled and uneditable nonetheless.
                textField.setDisable(true);
                textField.setEditable(false);
                break;
        }

        flowPane.getChildren().addAll(checkBox, taskNrLbl, editBtn, abortBtn, delBtn);
        setTop(flowPane);
        setCenter(textField);
    }

    /**
     * Marks a task as completed successfully or aborted depending on the parameter.
     * It's not possible to check, edit, abort or delete finished tasks.
     * @param success
     */
    private void endTask(boolean success) {
        if(success) {
            textField.setStyle(WhiteBoxApp.getDefaultStyle("green"));
            taskNrLbl.setStyle(WhiteBoxApp.getDefaultStyle("green"));
            status = Status.DONE;
        } else {
            textField.setStyle(WhiteBoxApp.getDefaultStyle("red"));
            taskNrLbl.setStyle(WhiteBoxApp.getDefaultStyle("red"));
            status = Status.ABORTED;
        }

        textField.setDisable(true);
        textField.setEditable(false);
        checkBox.setDisable(true);
        editBtn.setDisable(true);
        abortBtn.setDisable(true);
        delBtn.setDisable(true);
    }

    private void setupComponents() {
        flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setPadding(new Insets(0,5,0,5));

        taskNrLbl = new Label("Task #" + taskNr);
        taskNrLbl.setStyle(WhiteBoxApp.getDefaultStyle("black", "white"));

        editIcon = new ImagePane("/icons/edit_icon.png");
        abortIcon = new ImagePane("/icons/abort_icon.png");
        delIcon = new ImagePane("/icons/delete_icon.png");

        checkBox = new CheckBox();

        editBtn = new Button();
        editBtn.setGraphic(editIcon);

        abortBtn = new Button();
        abortBtn.setGraphic(abortIcon);

        delBtn = new Button();
        delBtn.setGraphic(delIcon);

        textField = new TextField();
        textField.setStyle(WhiteBoxApp.getDefaultStyle("black"));
        textField.setMaxWidth(WhiteBoxApp.getWidth());
    }

    private void addListeners() {
        // If checkBox is checked, mark the task as done.
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    endTask(true);
                }
            }
        });

        // If button is clicked the task becomes editable.
        editBtn.setOnAction(event ->
            {
                textField.setEditable(true);
                textField.setDisable(false);
            }
        );

        // If button is clicked, mark the task as aborted.
        abortBtn.setOnAction(event -> endTask(false));

        // If button is clicked, remove the according (this) task.
        delBtn.setOnAction(event -> TaskBox.removeTask(taskNr));

        // If enter key is pressed, make it impossible to edit textfield
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    textField.setEditable(false);
                    textField.setDisable(true);
                }
            }
        });
    }

    @Override
    public String toString() {
        return taskNr + ";" + status + ";" + textField.getText();
    }

    public enum Status {
        DONE,
        ABORTED,
        RUNNING;
    }
}
