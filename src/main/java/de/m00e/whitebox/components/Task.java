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
import javafx.scene.layout.GridPane;

public class Task extends GridPane {

    private Label taskNrLbl;
    private CheckBox checkBox;
    private Button editBtn, delBtn;
    private TextField textField;

    private ImagePane editIcon, delIcon;

    public Task(int taskNr) {
        setHgap(5);
        setVgap(5);
        setPadding(new Insets(0,5,0,5));

        taskNrLbl = new Label("Task #" + taskNr);
        taskNrLbl.setStyle(WhiteBoxMain.getDefaultButtonStyle());

        editIcon = new ImagePane("/edit_icon.png");
        delIcon = new ImagePane("/delete_icon.png");

        checkBox = new CheckBox();

        editBtn = new Button();
        editBtn.setGraphic(editIcon);

        delBtn = new Button();
        delBtn.setGraphic(delIcon);

        textField = new TextField();
        textField.setStyle("-fx-text-fill: black; -fx-font-size: 16px;");
        textField.setMaxWidth(WhiteBoxMain.getWidth());

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
        delBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                endTask(false);
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

        add(checkBox,0,0);
        add(taskNrLbl, 1,0);
        add(editBtn, 2,0);
        add(delBtn, 3,0);
        add(textField, 0,1,3,1);
    }

    /**
     * Marks a task as completed successfully or aborted depending on the parameter.
     * @param success
     */
    private void endTask(boolean success) {
        if(success)
            textField.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
        else
            textField.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");

        textField.setDisable(true);
        textField.setEditable(false);
        checkBox.setDisable(true);
        editBtn.setDisable(true);
        delBtn.setDisable(true);
    }
}
