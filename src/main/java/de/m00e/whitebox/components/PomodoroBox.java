package de.m00e.whitebox.components;

import de.m00e.whitebox.WhiteBoxMain;
import de.m00e.whitebox.listeners.AbortListener;
import de.m00e.whitebox.listeners.StartStopListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PomodoroBox extends VBox {

    private final double WIDTH = WhiteBoxMain.getWidth();
    private final double NODE_HEIGHT = WhiteBoxMain.getNodeHeight();
    private final double BUTTON_WIDTH = WhiteBoxMain.getButtonWidth();
    private final double BUTTON_HEIGHT = WhiteBoxMain.getButtonHeight();

    private static Button startBtn, abortBtn; //Start button is also stop button
    private static Label pomodoroLabel, infoLabel;
    private static LabeledComboBox sessionTimeBox, smallBreakTimeBox, longBreakTimeBox;

    private VBox controlPane;
    private static PomodoroWindow pomodoroWindow;

    private static boolean hasStarted, running;

    public PomodoroBox() {
        hasStarted = false;

        setupComponents();

        getChildren().addAll(pomodoroLabel, infoLabel, controlPane);
        setSpacing(10);
        setPadding(new Insets(0,10,10,10));
    }

    private void setupComponents() {
        pomodoroLabel = new Label("POMODORO");
        pomodoroLabel.setPrefHeight(NODE_HEIGHT);
        pomodoroLabel.setPrefWidth(WIDTH-20);
        pomodoroLabel.setAlignment(Pos.CENTER);
        pomodoroLabel.setFont(new Font("Arial", NODE_HEIGHT));

        infoLabel = new Label("Info: Times for the phases in minutes!");
        infoLabel.setAlignment(Pos.CENTER);
        infoLabel.setFont(new Font("Arial", NODE_HEIGHT/3));

        sessionTimeBox = new LabeledComboBox("Session-Time:", 20,60);
        smallBreakTimeBox = new LabeledComboBox("Small Breaks:", 5,15);
        longBreakTimeBox = new LabeledComboBox("Long Breaks:", 15,60);

        startBtn = new Button("Start");
        startBtn.setStyle(WhiteBoxMain.getDefaultButtonStyle("black"));
        startBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        startBtn.setOnAction(new StartStopListener());

        abortBtn = new Button("Abort");
        abortBtn.setStyle(WhiteBoxMain.getDefaultButtonStyle("black"));
        abortBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        abortBtn.setOnAction(new AbortListener());

        controlPane = new VBox();
        controlPane.setSpacing(7.5);
        controlPane.getChildren().addAll(new Separator(), sessionTimeBox, smallBreakTimeBox, longBreakTimeBox, new Separator(), startBtn, abortBtn);
    }

    /**
     * Method that is only called the first time the pomodoro timer has started.
     */
    public static void initStart() {
        hasStarted = true;
        running = true;
        sessionTimeBox.setDisable(true);
        smallBreakTimeBox.setDisable(true);
        longBreakTimeBox.setDisable(true);
        startBtn.setText("Stop");

        pomodoroWindow = new PomodoroWindow();
        pomodoroWindow.initializePhases(sessionTimeBox.getComboBox().getValue(),
                smallBreakTimeBox.getComboBox().getValue(),
                longBreakTimeBox.getComboBox().getValue());

        pomodoroWindow.runTimer();
    }

    /**
     * Stops the timer and closes the window.
     */
    public static void abortTimer() {
        startBtn.setText("Start");
        pomodoroWindow.getTimerStage().close();
        hasStarted = false;
        running = false;
        sessionTimeBox.setDisable(false);
        smallBreakTimeBox.setDisable(false);
        longBreakTimeBox.setDisable(false);
    }

    public static void setRunning(boolean run) {
        running = run;
    }

    public static Button getStartBtn() {
        return startBtn;
    }

    public static boolean isRunning() {
        return running;
    }

    public static boolean isHasStarted() {
        return hasStarted;
    }
}
