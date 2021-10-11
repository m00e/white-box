package de.m00e.whitebox.components;

import de.m00e.whitebox.WhiteBoxMain;
import de.m00e.whitebox.listeners.AbortListener;
import de.m00e.whitebox.listeners.StartStopListener;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.ToggleSwitch;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class PomodoroWindow {

    private final static double NODE_HEIGHT = WhiteBoxMain.getNodeHeight()+50;
    private final static double BUTTON_WIDTH = WhiteBoxMain.getButtonWidth();
    private final static double BUTTON_HEIGHT =  WhiteBoxMain.getButtonHeight();
    private final static double HEIGHT = 400; // Height of the whole timer pane

    private Text timerText;
    private Label statusLabel; // Status label shows whether session nr. ? or breaks running currently.
    private static Button startBtn, abortBtn;

    private Stage timerStage;
    private GridPane timerPane;
    private ToggleSwitch toggleSwitch;

    //Everything regarding timer
    private Timer timer;
    int second, minute;
    String ddSecond, ddMinute;
    private final DecimalFormat dFormat = new DecimalFormat("00");

    private int[] pomodoroTimes;
    private int phaseCount;

    //HTML-codes for pomodoro background colors
    private final String SESSION_COLOR = "#9fe97a", SMALL_BREAK_COLOR = "#e9937a", LONG_BREAK_COLOR = "#8a4531";

    //TODO: Play sound when session or break has ended
    public PomodoroWindow() {
        timerText = new Text("--:--");
        timerText.setFont(new Font("Arial", NODE_HEIGHT));

        statusLabel = new Label("Session - / -");
        statusLabel.setFont(new Font("Arial", NODE_HEIGHT/5));

        startBtn = new Button("Stop");
        startBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        startBtn.setOnAction(new StartStopListener());
        startBtn.getStyleClass().add("button-glassgrey");

        abortBtn = new Button("Abort");
        abortBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        abortBtn.setOnAction(new AbortListener());
        abortBtn.getStyleClass().add("button-glassgrey");

        timerStage = new Stage();
        timerStage.setHeight(HEIGHT/2);
        timerStage.setWidth(BUTTON_WIDTH+50);
        timerStage.setResizable(false);
        timerStage.setTitle("Pomodoro Timer");
        timerStage.setOnCloseRequest(event -> PomodoroBox.abortTimer());

        timerPane = new GridPane();
        Scene timerScene = new Scene(timerPane);
        timerScene.getStylesheets().add("/css/component-styles.css");

        toggleSwitch = new ToggleSwitch("Always Visible");
        toggleSwitch.setStyle(WhiteBoxMain.getDefaultStyle("black", "#9fe97a"));
        toggleSwitch.selectedProperty().addListener(((observable, oldValue, newValue) -> timerStage.setAlwaysOnTop(newValue)));

        timerPane.add(timerText,0,0,3,1);
        timerPane.add(statusLabel,1,2,1,1);
        timerPane.add(toggleSwitch,2,2,1,1);
        timerPane.add(startBtn, 1,3,1,1);
        timerPane.add(abortBtn, 2,3,1,1);

        GridPane.setHalignment(timerText, HPos.CENTER);
        GridPane.setHalignment(statusLabel, HPos.CENTER);
        GridPane.setHalignment(toggleSwitch, HPos.CENTER);
        GridPane.setHalignment(startBtn, HPos.CENTER);
        GridPane.setHalignment(abortBtn, HPos.CENTER);

        timerStage.setScene(timerScene);
        timerStage.show();
    }

    /**
     * Function that provides the countdown timer functionality.
     */
    public void runTimer() {
        if(phaseCount == 8) { //Stop entire timer when last phase is done.
            return;
        }

        initializeTimer(pomodoroTimes[phaseCount]);
        setStatus();

        timer = new Timer("timer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(PomodoroBox.isRunning()) {
                    second--;
                }
                ddSecond = dFormat.format(second);
                ddMinute = dFormat.format(minute);

                if (second <= -1) {
                    second = 59;
                    minute--;
                    ddSecond = dFormat.format(second);
                    ddMinute = dFormat.format(minute);
                }

                Platform.runLater(() -> timerText.setText(ddMinute + ":" + ddSecond));

                if (minute == 0 && second == 0) {
                    // Stop current timer and immediately start new timer
                    phaseCount++;
                    timer.cancel();
                    runTimer();
                }
            }
        }, 0L, 1000L);
    }

    /**
     * Initialize pomodoro phases and phase count. One Pomodoro consists of
     * 1. session, small break, 2. session, small break, 3. session, small break, 4. session, long break.
     */
    public void initializePhases(int sessionTime, int smallBreak, int longBreak) {
        phaseCount = 0;
        pomodoroTimes = new int[8];
        pomodoroTimes[0] = sessionTime;
        pomodoroTimes[1] = smallBreak;
        pomodoroTimes[2] = sessionTime;
        pomodoroTimes[3] = smallBreak;
        pomodoroTimes[4] = sessionTime;
        pomodoroTimes[5] = smallBreak;
        pomodoroTimes[6] = sessionTime;
        pomodoroTimes[7] = longBreak;
    }

    /**
     * Sets status label and background color according to the current pomodoro phase.
     */
    public void setStatus() {
        switch(getPhaseFromCount()) {
            case SESSION -> {
                Platform.runLater(() -> statusLabel.setText("Session: " + getSessionNumber() + " / 4"));
                setComponentColor(SESSION_COLOR);
            }

            case SMALL_BREAK -> {
                Platform.runLater(() -> statusLabel.setText("Small break"));
                setComponentColor(SMALL_BREAK_COLOR);
            }

            case LONG_BREAK -> {
                Platform.runLater(() -> statusLabel.setText("Long break"));
                setComponentColor(LONG_BREAK_COLOR);
            }
        }
    }

    /**
     * Set background colors for entire pane, buttons and toggle switch
     * @param color JavaFX color or HTML-color-code
     */
    private void setComponentColor(String color) {
        timerPane.setStyle("-fx-background-color: " + color + ";");
        toggleSwitch.setStyle(WhiteBoxMain.getDefaultStyle("black", color));
    }

    public void initializeTimer(int minutes) {
        this.minute = minutes;
        second = 0;
    }

    public Stage getTimerStage() {
        return timerStage;
    }

    /**
     * Translates phase count into session nr.
     * @return Session nr. (1,2,3,4)
     */
    public int getSessionNumber() {
        if(phaseCount%2 == 0) {
            return phaseCount/2+1;
        }

        return -1; // Currently in a break.
    }

    /**
     * Translates current phase number into PomodoroPhase-enum.
     * @return PomodoroPhase enum
     */
    public PomodoroPhase getPhaseFromCount() {
        if(phaseCount%2 == 0) {
            return PomodoroPhase.SESSION;
        } else if(phaseCount%2 == 1 && phaseCount < 7) {
            return PomodoroPhase.SMALL_BREAK;
        }
        return PomodoroPhase.LONG_BREAK;
    }

    public static Button getStartBtn() {
        return startBtn;
    }

    public enum PomodoroPhase {
        LONG_BREAK,
        SESSION,
        SMALL_BREAK
    }
}
