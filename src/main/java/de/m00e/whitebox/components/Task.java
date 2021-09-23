package de.m00e.whitebox.components;

import de.m00e.whitebox.WhiteBoxMain;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;


public class Task extends FlowPane {

    private CheckBox checkBox;
    private Button button;
    private TextField textField;

    public Task() {
        checkBox = new CheckBox();
        button = new Button("[X]");
        textField = new TextField();
        textField.setStyle("-fx-text-fill: black; -fx-font-size: 16px;");

        // If checkBox is checked, mark the task as done.
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    endTask(true);
                }
            }
        });

        // If button is clicked, mark the task as aborted.
        button.setOnAction(new EventHandler<ActionEvent>() {
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

        this.setPadding(new Insets(10,10,10,10));
        this.getChildren().addAll(checkBox, button, textField);

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
        button.setDisable(true);
    }
}
