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
    private ScrollPane sp;
    private static VBox taskListBox;

    private static ArrayList<Task> taskArrayList;
    private static int taskCounter = 1;

    public TaskBox() {
        setupComponents();
        addListeners();

        getChildren().addAll(taskLabel, addBtn, clrBtn, new Separator(), sp);
        setSpacing(10);
        setPadding(new Insets(0,10,10,10));
    }

    private void setupComponents() {
        taskArrayList = new ArrayList<Task>();

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

        taskListBox = new VBox();

        // sp just represents the actual task list with the possibility of scrolling.
        sp = new ScrollPane();
        sp.setContent(taskListBox);
        sp.setPannable(true); // it means that the user should be able to pan the viewport by using the mouse.
        sp.setFitToWidth(true);
    }

    private void addListeners() {
        // Add single tasks to task list.
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Task task = new Task(taskCounter);
                taskListBox.getChildren().add(task);
                taskArrayList.add(task);
                System.out.println(taskArrayList.size());
                taskCounter++;
            }
        });

        // Delete all tasks at once.
        clrBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                taskListBox.getChildren().removeAll(taskListBox.getChildren());
                taskArrayList.removeAll(taskArrayList);
                System.out.println(taskArrayList.size());
                taskCounter = 1;
            }
        });
    }

    public static ArrayList<Task> getTaskArrayList() {
        return taskArrayList;
    }

    public static VBox getTaskListBox() {
        return taskListBox;
    }
}
