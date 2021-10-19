package de.m00e.whitebox.components;

import de.m00e.whitebox.WhiteBoxApp;
import de.m00e.whitebox.listeners.LoadTaskListener;
import de.m00e.whitebox.listeners.SaveTaskListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.HashMap;

public class TaskBox extends VBox {

    private static File currFile; // Remember the file the user is working on.

    private final double BUTTON_WIDTH = WhiteBoxApp.getButtonWidth();
    private final double BUTTON_HEIGHT = WhiteBoxApp.getButtonHeight()-20;

    private static Button addBtn, clrBtn, saveBtn, saveAsBtn, loadBtn;
    private static TextField newTask;

    private static Label currFileLabel;
    private static ScrollPane sp;
    private static VBox taskListBox;
    private static ImagePane imgPane;


    private static HashMap<Integer, Task> taskMap;
    private static int taskCounter = 1;

    private GridPane btnPane;
    private BorderPane saveBtnPane; // Containing save and save-as button.

    public TaskBox() {
        setupComponents();
        addListeners();

        saveBtnPane.setCenter(saveBtn);
        saveBtnPane.setRight(saveAsBtn);

        btnPane.add(newTask, 0,0,4,1);
        btnPane.add(addBtn,5,0,1,1);
        btnPane.add(clrBtn,0,1,1,1);
        btnPane.add(loadBtn,1,1,1,1);
        btnPane.add(saveBtnPane,2,1,1,1);

        getChildren().addAll(imgPane, btnPane, currFileLabel, new Separator(), sp);
        setSpacing(10);
        setPadding(new Insets(0,10,10,10));
    }

    private void setupComponents() {
        btnPane = new GridPane();
        btnPane.setPadding(new Insets(0,5,0,5));
        btnPane.setHgap(5);
        btnPane.setVgap(5);

        saveBtnPane = new BorderPane();

        taskMap = new HashMap<>();

        addBtn = new Button("Add");
        addBtn.getStyleClass().add("button-glassgrey");
        addBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        clrBtn = new Button("Clear All");
        clrBtn.getStyleClass().add("button-glassgrey");
        clrBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        saveBtn = new Button("Save Tasks");
        saveBtn.getStyleClass().add("button-glassgrey");
        saveBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        saveAsBtn = new Button("...");
        saveAsBtn.getStyleClass().add("button-glassgrey");
        saveAsBtn.setPrefHeight(BUTTON_HEIGHT);

        loadBtn = new Button("Load Tasks");
        loadBtn.getStyleClass().add("button-glassgrey");
        loadBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        imgPane = new ImagePane("/icons/tasks_icon.png"); // Load main icon

        currFileLabel = new Label();

        newTask = new TextField();
        newTask.setStyle(WhiteBoxApp.getDefaultStyle("black"));

        taskListBox = new VBox();
        taskListBox.setSpacing(5);

        // sp just represents the actual task list with the scrolling function.
        sp = new ScrollPane();
        sp.setContent(taskListBox);
        sp.setPannable(true); // it means that the user should be able to pan the viewport by using the mouse.
        sp.setFitToWidth(true);
    }

    private void addListeners() {
        // Add single tasks to task list.
        addBtn.setOnAction(event -> {
            addTask(newTask.getText());
        });

        // Delete all tasks at once.
        clrBtn.setOnAction(event -> resetTaskBox());

        saveBtn.setOnAction(new SaveTaskListener(false)); // Only save into new file if no current file exists
        saveAsBtn.setOnAction(new SaveTaskListener(true)); // Always save into a new file
        loadBtn.setOnAction(new LoadTaskListener());

        newTask.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)) {
                addTask(newTask.getText());
            }
        });
    }

    /**
     * Adds a new task to the task list.
     */
    private void addTask(String taskStr) {
        Task task = new Task(taskCounter, taskStr);
        taskListBox.getChildren().add(task);
        taskMap.put(taskCounter,task);
        taskCounter++;

        newTask.setText("");
    }

    /**
     * Remove a task from the task list.
     * @param taskNr Task that should be removed.
     */
    public static void removeTask(int taskNr) {
        taskListBox.getChildren().remove(taskMap.get(taskNr));
        taskMap.remove(taskNr);

        if(taskMap.isEmpty()) // Reset counter if there are tasks.
            taskCounter = 1;
    }

    /**
     * Method to save the tasks of the task box into a selected file.
     * @param tasksFile File to save in
     */
    public static void saveTasks(File tasksFile) throws IOException {
        PrintWriter writer = new PrintWriter(tasksFile);
        taskListBox.getChildren().forEach(t -> writer.write(t.toString() + "\n"));

        writer.flush();
        writer.close();

        currFile = tasksFile;
        currFileLabel.setText("Saved to " + currFile.getAbsolutePath());
    }

    /**
     * Method to load the tasks of a .tasks file into the task box and the hashmap.
     * @param tasksFile File to load from
     */
    public static void loadTasks(File tasksFile) throws IOException {
        resetTaskBox(); // Task box and hashmap must be cleared to refill them later

        // Read and split task strings at semicolons and add new tasks to task box and hashmap.
        FileReader fileReader = new FileReader(tasksFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while((line = bufferedReader.readLine()) != null) {
            String[] info = line.split(";");
            taskListBox.getChildren().add(new Task(info));
        }
        bufferedReader.close();

        currFile = tasksFile;
        currFileLabel.setText("Loaded from " + currFile.getAbsolutePath());
    }

    /**
     * Deletes all remaining tasks and sets counter to 1.
     */
    public static void resetTaskBox() {
        taskListBox.getChildren().removeAll(taskListBox.getChildren());
        taskMap.clear();
        taskCounter = 1;
    }

    public static File getRecentFile() {
        return currFile;
    }
}
