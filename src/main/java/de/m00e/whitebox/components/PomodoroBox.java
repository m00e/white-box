package de.m00e.whitebox.components;

import de.m00e.whitebox.WhiteBoxMain;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PomodoroBox extends VBox {

    private final double WIDTH = WhiteBoxMain.getWidth();
    private final double NODE_HEIGHT = WhiteBoxMain.getNodeHeight();
    private final double BUTTON_WIDTH = WhiteBoxMain.getButtonWidth();
    private final double BUTTON_HEIGHT = WhiteBoxMain.getButtonHeight();

    private static Button startBtn, abortBtn; //Start button is also stop button
    private static Label pomodoroLabel;
    private static LabeledComboBox sessionTimeBox, smallBreakTimeBox, longBreakTimeBox;

    private VBox controlPane;
    private static PomodoroWindow pomodoroWindow;

    private static boolean hasStarted;

    public PomodoroBox() {
        hasStarted = false;

        setupComponents();
        addListeners();

        getChildren().addAll(pomodoroLabel, controlPane);
        setSpacing(10);
        setPadding(new Insets(0,10,10,10));
    }

    private void setupComponents() {
        pomodoroLabel = new Label("POMODORO");
        pomodoroLabel.setPrefHeight(NODE_HEIGHT);
        pomodoroLabel.setPrefWidth(WIDTH-20);
        pomodoroLabel.setAlignment(Pos.CENTER);
        pomodoroLabel.setFont(new Font("Arial", NODE_HEIGHT));

        sessionTimeBox = new LabeledComboBox("Session-Time (in minutes)", 20,60);
        smallBreakTimeBox = new LabeledComboBox("Small Breaks (in minutes)", 5,15);
        longBreakTimeBox = new LabeledComboBox("Big Breaks (in minutes)", 15,60);

        startBtn = new Button("Start");
        startBtn.setStyle(WhiteBoxMain.getDefaultButtonStyle());
        startBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        abortBtn = new Button("Abort");
        abortBtn.setStyle(WhiteBoxMain.getDefaultButtonStyle());
        abortBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        controlPane = new VBox();
        controlPane.getChildren().addAll(sessionTimeBox, smallBreakTimeBox, longBreakTimeBox, startBtn, abortBtn);
    }

    public void addListeners() {
        startBtn.setOnAction(event ->
            {
                // Only set true the first time the timer has started.
                if(!hasStarted)
                    initStart();

                if(startBtn.getText().equals("Stop")) {
                    startBtn.setText("Start");
                    //pomodoroWindow.getTimer().stop();
                } else {
                    startBtn.setText("Stop");
                    System.out.println("test2");
                    //pomodoroWindow.getTimer().start();
                }
            });

        abortBtn.setOnAction(event -> abortTimer());

    }

    /**
     * Method that is only called the first time the pomodoro timer has started.
     */
    private void initStart() {
        hasStarted = true;
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
        //pomodoroWindow.getTimer().stop();
        pomodoroWindow.getTimerStage().close();
        hasStarted = false;
        sessionTimeBox.setDisable(false);
        smallBreakTimeBox.setDisable(false);
        longBreakTimeBox.setDisable(false);
    }
}
