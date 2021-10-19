package de.m00e.whitebox.components;

import de.m00e.whitebox.WhiteBoxApp;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class Task extends BorderPane {

    private Label taskNrLbl;
    private CheckBox checkBox;
    private TextField textField;

    private final int taskNr;

    private final Insets insets = new Insets(5);

    private final double BUTTON_HEIGHT = WhiteBoxApp.getButtonHeight()-20;

    public Task(int taskNr, String taskStr) {
        this.taskNr = taskNr;

        setupComponents();
        addListeners();

        textField.setText(taskStr);
    }

    /**
     * Alternative constructor that is called if tasks are being loaded into {@link de.m00e.whitebox.components.TaskBox}.
     * @param info String array containing task number and actual task string.
     */
    public Task(String[] info) {
        this.taskNr = Integer.parseInt(info[0]);

        setupComponents();
        addListeners();

        if (info.length == 2)
            textField.setText(info[1]);
        else
            textField.setText("");
    }

    private void setupComponents() {
        taskNrLbl = new Label("Task #" + taskNr);
        taskNrLbl.setFont(new Font("Arial", 16));
        taskNrLbl.setStyle(WhiteBoxApp.getDefaultStyle("black"));
        taskNrLbl.setPrefHeight(BUTTON_HEIGHT);

        checkBox = new CheckBox();
        checkBox.setPrefHeight(BUTTON_HEIGHT);

        textField = new TextField();
        textField.setStyle(WhiteBoxApp.getDefaultStyle("black"));
        textField.setMaxWidth(WhiteBoxApp.getWidth());
        textField.setPrefHeight(BUTTON_HEIGHT);

        this.setLeft(taskNrLbl);
        this.setCenter(textField);
        this.setRight(checkBox);

        setMargin(taskNrLbl, insets);
        setMargin(textField, insets);
        setMargin(checkBox, insets);
    }

    private void addListeners() {
        // If checkBox is checked, mark the task as done.
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                TaskBox.removeTask(taskNr);
            }
        });
    }

    @Override
    public String toString() {
        return taskNr + ";" + textField.getText();
    }
}
