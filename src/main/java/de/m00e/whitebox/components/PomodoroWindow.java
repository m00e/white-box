package de.m00e.whitebox.components;

import de.m00e.whitebox.WhiteBoxMain;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.controlsfx.control.ToggleSwitch;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class PomodoroWindow extends BorderPane {

    private final static double NODE_HEIGHT = WhiteBoxMain.getNodeHeight()+50;
    private final static double BUTTON_WIDTH = WhiteBoxMain.getButtonWidth();
    private final static double HEIGHT = 400; // Height of the whole timer pane

    private Text timerText;
    private Label statusLabel; // Status label shows whether session nr. ? or breaks running currently.

    private Stage timerStage;
    private Scene timerScene;
    private GridPane timerPane;
    private ToggleSwitch toggleSwitch;

    //Everything regarding timer
    private Timer timer;
    int second, minute;
    String ddSecond, ddMinute;
    private DecimalFormat dFormat = new DecimalFormat("00");

    private int[] pomodoroPhases;
    private int phaseCount;

    //TODO: With Threading?
    //TODO: Session = Green, Break = Red
    //TODO: Play sound when session or break has ended
    //TODO: Close external window when main window is closed
    public PomodoroWindow() {
        timerText = new Text("--:--");
        timerText.setTextAlignment(TextAlignment.CENTER);
        timerText.setFont(new Font("Arial", NODE_HEIGHT));

        statusLabel = new Label("Session - / -");
        statusLabel.setAlignment(Pos.CENTER);
        statusLabel.setFont(new Font("Arial", NODE_HEIGHT/4));

        timerStage = new Stage();
        timerStage.setHeight(HEIGHT/2);
        timerStage.setWidth(BUTTON_WIDTH+50);
        timerStage.setResizable(false);
        timerStage.setTitle("Pomodoro Timer");
        timerStage.setOnCloseRequest(event -> {timerStage.setIconified(true);});

        timerPane = new GridPane();
        timerScene = new Scene(timerPane);
        toggleSwitch = new ToggleSwitch("Set in foreground");
        toggleSwitch.setStyle(WhiteBoxMain.getDefaultButtonStyle());
        // TODO: Add timerStage.setAlwaysOnTop(toggleSwitch.isSelected()) to toggleSwitch listener

        timerPane.add(timerText,0,0,2,1);
        timerPane.add(statusLabel,1,2,1,1);
        timerPane.add(toggleSwitch,2,2,1,1);

        timerStage.setScene(timerScene);
        timerStage.show();
    }

    public void runTimer() {
        if(phaseCount == 8) { //Stop entire timer when last phase is done.
            return;
        }

        initializeTimer(pomodoroPhases[phaseCount]);

        timer = new Timer("timer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                second--;
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
                    phaseCount++;
                    timer.cancel();
                    runTimer();
                }
            }
        }, 0L, 50L);
    }

    /**
     * Initialize pomodoro phases and phase count. One Pomodoro consists of
     * 1. session, small break, 2. session, small break, 3. session, small break, 4. session, long break.
     * @param sessionTime
     * @param smallBreak
     * @param longBreak
     */
    public void initializePhases(int sessionTime, int smallBreak, int longBreak) {
        phaseCount = 0;
        pomodoroPhases = new int[8];
        pomodoroPhases[0] = sessionTime;
        pomodoroPhases[1] = smallBreak;
        pomodoroPhases[2] = sessionTime;
        pomodoroPhases[3] = smallBreak;
        pomodoroPhases[4] = sessionTime;
        pomodoroPhases[5] = smallBreak;
        pomodoroPhases[6] = sessionTime;
        pomodoroPhases[7] = longBreak;
    }

    public void initializeTimer(int minutes) {
        this.minute = minutes;
        second = 0;
    }

    public void closeTimerStage() {timerStage.close();}

    public Timer getTimer() {return timer;}
    public Stage getTimerStage() {
        return timerStage;
    }
}
