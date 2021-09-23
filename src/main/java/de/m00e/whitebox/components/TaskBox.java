package de.m00e.whitebox.components;

import de.m00e.whitebox.WhiteBoxMain;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class TaskBox extends VBox {

    private final double WIDTH = WhiteBoxMain.getWidth();
    private final double NODE_HEIGHT = WhiteBoxMain.getNodeHeight();
    private final double BUTTON_WIDTH = WhiteBoxMain.getButtonWidth();
    private final double BUTTON_HEIGHT = WhiteBoxMain.getButtonHeight()-20;

    private Button addBtn, clrBtn;
    private Label taskLabel;

    public TaskBox() {
        setupComponents();

        getChildren().addAll(taskLabel, addBtn, clrBtn);
        setSpacing(10);
        setPadding(new Insets(0,10,10,10));
    }

    private void setupComponents() {
        addBtn = new Button("Add New Task");
        addBtn.setStyle(WhiteBoxMain.getDefaultButtonStyle());
        addBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        clrBtn = new Button("Clear All Tasks");
        clrBtn.setStyle(WhiteBoxMain.getDefaultButtonStyle());
        clrBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        taskLabel = new Label("TASKS");
        taskLabel.setPrefHeight(NODE_HEIGHT);
        taskLabel.setPrefWidth(WIDTH-20);
        taskLabel.setAlignment(Pos.CENTER);
        taskLabel.setFont(new Font("Courier New", NODE_HEIGHT));
    }
}
