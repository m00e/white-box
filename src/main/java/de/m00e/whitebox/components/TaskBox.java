package de.m00e.whitebox.components;

import de.m00e.whitebox.WhiteBoxMain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class TaskBox extends VBox {

    private final double WIDTH = WhiteBoxMain.getWidth();
    private final double NODE_HEIGHT = WhiteBoxMain.getNodeHeight();
    private final double BUTTON_WIDTH = WhiteBoxMain.getButtonWidth();
    private final double BUTTON_HEIGHT = WhiteBoxMain.getButtonHeight()-20;

    private Button addBtn, clrBtn;
    private Label taskLabel;

    public TaskBox() {
        setupComponents();


        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1));
        tasks.add(new Task(2));
        tasks.add(new Task(3));

        // sp just represents the taskBox with scrolling functionality
        ScrollPane sp = new ScrollPane();
        sp.setContent();
        sp.setPannable(true); // it means that the user should be able to pan the viewport by using the mouse.
        sp.setFitToWidth(true);

        getChildren().addAll(taskLabel, addBtn, clrBtn, new Separator(), sp);
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

        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        clrBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
    }
}
