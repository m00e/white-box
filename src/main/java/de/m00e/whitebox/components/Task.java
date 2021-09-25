package de.m00e.whitebox.components;

import de.m00e.whitebox.WhiteBoxMain;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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

    public Task(int taskNr) {
        this.taskNr = taskNr;

        setupComponents();
        addListeners();

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
            textField.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
            taskNrLbl.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
        } else {
            textField.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            taskNrLbl.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
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
        taskNrLbl.setStyle(WhiteBoxMain.getDefaultButtonStyle());

        editIcon = new ImagePane("/edit_icon.png");
        abortIcon = new ImagePane("/abort_icon.png");
        delIcon = new ImagePane("/delete_icon.png");

        checkBox = new CheckBox();

        editBtn = new Button();
        editBtn.setGraphic(editIcon);

        abortBtn = new Button();
        abortBtn.setGraphic(abortIcon);

        delBtn = new Button();
        delBtn.setGraphic(delIcon);

        textField = new TextField();
        textField.setStyle("-fx-text-fill: black; -fx-font-size: 16px;");
        textField.setMaxWidth(WhiteBoxMain.getWidth());
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
        editBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textField.setEditable(true);
                textField.setDisable(false);
            }
        });

        // If button is clicked, mark the task as aborted.
        abortBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                endTask(false);
            }
        });

        // If button is clicked, remove the according (this) task.
        delBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TaskBox.removeTask(taskNr);
            }
        });

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
}
