package de.m00e.whitebox.components;

import de.m00e.whitebox.WhiteBoxMain;
import de.m00e.whitebox.listeners.LoadTaskListener;
import de.m00e.whitebox.listeners.SaveTaskListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskBox extends VBox {

    private final double WIDTH = WhiteBoxMain.getWidth();
    private final double NODE_HEIGHT = WhiteBoxMain.getNodeHeight();
    private final double BUTTON_WIDTH = WhiteBoxMain.getButtonWidth();
    private final double BUTTON_HEIGHT = WhiteBoxMain.getButtonHeight()-20;

    private Button addBtn, clrBtn, saveBtn, loadBtn;
    private Label taskLabel;
    private ScrollPane sp;
    private static VBox taskListBox;

    private static HashMap<Integer, Task> taskMap;
    private static int taskCounter = 1;

    private GridPane btnPane;

    public TaskBox() {
        setupComponents();
        addListeners();

        btnPane.add(addBtn,0,0);
        btnPane.add(clrBtn,0,1);
        btnPane.add(saveBtn,1,0);
        btnPane.add(loadBtn,1,1);
        getChildren().addAll(taskLabel, btnPane, new Separator(), sp);
        setSpacing(10);
        setPadding(new Insets(0,10,10,10));
    }

    private void setupComponents() {
        btnPane = new GridPane();
        btnPane.setPadding(new Insets(0,5,0,5));
        btnPane.setHgap(5);
        btnPane.setVgap(5);

        taskMap = new HashMap<Integer, Task>();

        addBtn = new Button("Add New Task");
        addBtn.setStyle(WhiteBoxMain.getDefaultButtonStyle());
        addBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        clrBtn = new Button("Clear All Tasks");
        clrBtn.setStyle(WhiteBoxMain.getDefaultButtonStyle());
        clrBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        saveBtn = new Button("Save Task List");
        saveBtn.setStyle(WhiteBoxMain.getDefaultButtonStyle());
        saveBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        loadBtn = new Button("Load Task List");
        loadBtn.setStyle(WhiteBoxMain.getDefaultButtonStyle());
        loadBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

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
                taskMap.put(taskCounter,task);
                taskCounter++;
            }
        });

        // Delete all tasks at once.
        clrBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetTaskBox();
            }
        });

        saveBtn.setOnAction(new SaveTaskListener());
        loadBtn.setOnAction(new LoadTaskListener());
    }

    /**
     * Remove a task from the task list.
     * @param taskNr Task that should be removed.
     */
    public static void removeTask(int taskNr) {
        taskListBox.getChildren().remove(taskMap.get(taskNr));
        taskMap.remove(taskNr);
    }

    /**
     * Method to save the tasks of the task box into a selected file.
     * @param tasksFile File to save in
     */
    public static void saveTasks(File tasksFile) throws IOException {
        PrintWriter writer;
        if(!tasksFile.exists())
            tasksFile.createNewFile();

        writer = new PrintWriter(tasksFile);

        taskListBox.getChildren().forEach(t -> writer.write(t.toString() + "\n"));

        writer.flush();
        writer.close();
    }

    /**
     * Method to load the tasks of a .tasks file into the task box and the hashmap.
     * @param tasksFile File to load from
     */
    public static void loadTasks(File tasksFile) throws IOException {
        //TODO: Automatically save file as .tasks file.
        //TODO: Maybe reset task box AFTER file has been read?
        resetTaskBox(); // Task box and hashmap must be cleared to refill them later

        // Read and split task strings at semicolons and add new tasks to task box and hashmap.
        FileReader fileReader = new FileReader(tasksFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        while((line = bufferedReader.readLine()) != null) {
            String[] info = line.split(";");
            taskListBox.getChildren().add(new Task(info));
        }
        bufferedReader.close();

    }

    /**
     * Deletes all remaining tasks and sets counter to 1.
     */
    public static void resetTaskBox() {
        taskListBox.getChildren().removeAll(taskListBox.getChildren());
        taskMap.clear();
        taskCounter = 1;
    }
}
