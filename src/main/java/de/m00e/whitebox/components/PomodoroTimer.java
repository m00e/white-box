package de.m00e.whitebox.components;

import de.m00e.whitebox.WhiteBoxMain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.controlsfx.control.ToggleSwitch;

public class PomodoroTimer extends BorderPane {

    private final static double WIDTH = WhiteBoxMain.getWidth();
    private final static double NODE_HEIGHT = WhiteBoxMain.getNodeHeight()+50;
    private final static double BUTTON_WIDTH = WhiteBoxMain.getButtonWidth();
    private final static double BUTTON_HEIGHT = WhiteBoxMain.getButtonHeight();
    private final static double HEIGHT = 400; // Height of the whole timer pane

    private static Label timerLabel, statusLabel; // Status label shows whether session nr. ? or breaks running currently.

    // Components for external window
    private static Stage timerStage;
    private static Scene timerScene;
    private static GridPane timerPane;
    private static ToggleSwitch toggleSwitch;

    //TODO: With Threading?
    //TODO: Session = Green, Break = Red
    //TODO: Play sound when session or break has ended
    public PomodoroTimer() {
        timerLabel = new Label("20:00");
        timerLabel.setAlignment(Pos.CENTER);
        timerLabel.setFont(new Font("Arial", NODE_HEIGHT));

        statusLabel = new Label("Session - / -");
        statusLabel.setAlignment(Pos.CENTER);
        statusLabel.setFont(new Font("Arial", NODE_HEIGHT/4));

        this.setHeight(HEIGHT);
        this.setStyle("-fx-border-color: black"); // Adds a black border to the pane.

        this.setCenter(timerLabel);
        this.setBottom(statusLabel);
    }

    /**
     * Creates an external timer that only shows the remaining time and status of the pomodoro session.
     */
    public static void createTimerStage() {
        timerStage = new Stage();
        timerStage.setHeight(HEIGHT/2);
        timerStage.setWidth(BUTTON_WIDTH+50);
        timerStage.setResizable(false);
        timerStage.setTitle("Pomodoro Timer");

        timerPane = new GridPane();
        timerScene = new Scene(timerPane);
        toggleSwitch = new ToggleSwitch("Set in foreground");
        // TODO: Add timerStage.setAlwaysOnTop(toggleSwitch.isSelected()) to toggleSwitch listener

        timerPane.add(timerLabel,0,0,2,1);
        timerPane.add(statusLabel,1,2,1,1);
        timerPane.add(toggleSwitch,2,2,1,1);

        timerStage.setScene(timerScene);
        timerStage.show();
    }

    /**
     * Closes external timer window.
     */
    public static void closeTimerStage() {
        timerStage.close();
    }


    public static void setSessionTime() {

    }

    public static void setSmallBreak() {

    }

    public static void setBigBreak() {

    }
}
