package de.m00e.whitebox.components;

import de.m00e.whitebox.WhiteBoxApp;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;

public class Task extends BorderPane {

    private Label taskNrLbl;
    private CheckBox checkBox;
    private Button delBtn;
    private TextField textField;

    private FlowPane flowPane; // Main menu of each task

    private final int TASKNR;
    private Status status;

    public Task(int taskNr) {
        this.TASKNR = taskNr;
        status = Status.RUNNING;

        setupComponents();
        addListeners();

        flowPane.getChildren().addAll(checkBox, taskNrLbl, delBtn);
        setTop(flowPane);
        setCenter(textField);
    }

    /**
     * Alternative constructor that is called if tasks are being loaded into {@link de.m00e.whitebox.components.TaskBox}.
     * @param info String array containing task number, {@link de.m00e.whitebox.components.Task.Status}
     *             and actual task string.
     */
    public Task(String[] info) {
        this.TASKNR = Integer.parseInt(info[0]);
        this.status = Status.valueOf(info[1]);

        setupComponents();
        addListeners();

        if (info.length == 3)
            textField.setText(info[2]);
        else
            textField.setText("");

        if(status == Status.DONE) {
            checkBox.setSelected(true);
            endTask();
        }

        flowPane.getChildren().addAll(checkBox, taskNrLbl, delBtn);
        setTop(flowPane);
        setCenter(textField);
    }

    /**
     * Marks a task as completed.
     * It's not possible to check, edit nor delete finished tasks.
     */
    private void endTask() {
        textField.setStyle(WhiteBoxApp.getDefaultStyle("green"));
        taskNrLbl.setStyle(WhiteBoxApp.getDefaultStyle("green"));
        status = Status.DONE;

        textField.setDisable(true);
        textField.setEditable(false);
        checkBox.setDisable(true);
        delBtn.setDisable(true);
    }

    private void setupComponents() {
        flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setPadding(new Insets(0,5,0,5));

        taskNrLbl = new Label("Task #" + TASKNR);
        taskNrLbl.setFont(new Font("Arial", 16));
        taskNrLbl.setStyle(WhiteBoxApp.getDefaultStyle("black"));

        ImagePane delIcon = new ImagePane("/icons/delete_icon.png");

        checkBox = new CheckBox();

        delBtn = new Button();
        delBtn.setGraphic(delIcon);

        textField = new TextField();
        textField.setStyle(WhiteBoxApp.getDefaultStyle("black"));
        textField.setMaxWidth(WhiteBoxApp.getWidth());
    }

    private void addListeners() {
        // If checkBox is checked, mark the task as done.
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                endTask();
            }
        });

        // If button is clicked, remove the according (this) task.
        delBtn.setOnAction(event -> TaskBox.removeTask(TASKNR));
    }

    @Override
    public String toString() {
        return TASKNR + ";" + status + ";" + textField.getText();
    }

    public enum Status {
        DONE,
        RUNNING
    }
}
