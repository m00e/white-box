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

    private Button startBtn, abortBtn; //Start button is also stop button
    private Label pomodoroLabel;
    private LabeledComboBox sessionTimeBox, smallBreakTimeBox, bigBreakTimeBox;

    private VBox controlPane;
    private PomodoroTimer pomodoroTimer;

    private static boolean running, hasStarted;

    public PomodoroBox() {
        running = false;
        hasStarted = false;

        setupComponents();
        addListeners();

        getChildren().addAll(pomodoroLabel, pomodoroTimer, controlPane);
        setSpacing(10);
        setPadding(new Insets(0,10,10,10));
    }

    private void setupComponents() {
        pomodoroLabel = new Label("POMODORO");
        pomodoroLabel.setPrefHeight(NODE_HEIGHT);
        pomodoroLabel.setPrefWidth(WIDTH-20);
        pomodoroLabel.setAlignment(Pos.CENTER);
        pomodoroLabel.setFont(new Font("Arial", NODE_HEIGHT));

        pomodoroTimer = new PomodoroTimer();

        sessionTimeBox = new LabeledComboBox("Session-Time (in minutes)", 20,60);
        smallBreakTimeBox = new LabeledComboBox("Small Breaks (in minutes)", 5,15);
        bigBreakTimeBox = new LabeledComboBox("Big Breaks (in minutes)", 15,60);

        startBtn = new Button("Start");
        startBtn.setStyle(WhiteBoxMain.getDefaultButtonStyle());
        startBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        abortBtn = new Button("Abort");
        abortBtn.setStyle(WhiteBoxMain.getDefaultButtonStyle());
        abortBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        controlPane = new VBox();
        controlPane.getChildren().addAll(sessionTimeBox, smallBreakTimeBox, bigBreakTimeBox, startBtn, abortBtn);
        controlPane.setStyle("-fx-border-color: black"); // Adds a black border to the pane.
    }

    public void addListeners() {
        startBtn.setOnAction(event ->
            {
                // Only set true the first time the timer has started.
                if(!hasStarted)
                    initStart();

                if(isRunning()) {
                    startBtn.setText("Start");
                    running = false;
                } else {
                    startBtn.setText("Stop");
                    running = true;
                }
            });

        abortBtn.setOnAction(event ->
        {
            startBtn.setText("Start");
            running = false;
            hasStarted = false;
            sessionTimeBox.setDisable(false);
            smallBreakTimeBox.setDisable(false);
            bigBreakTimeBox.setDisable(false);

            PomodoroTimer.closeTimerStage();
        });

        // TODO: Add listeners for all comboboxes
        /*cmbComp.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            System.out.println(newValue);
        });*/

    }

    /**
     * Method that is only called the first time the pomodoro timer has started.
     */
    private void initStart() {
        hasStarted = true;
        sessionTimeBox.setDisable(true);
        smallBreakTimeBox.setDisable(true);
        bigBreakTimeBox.setDisable(true);

        PomodoroTimer.createTimerStage();
    }

    /**
     * Returns whether timer is running or not.
     * @return running
     */
    public static boolean isRunning() {
        return running;
    }
}
