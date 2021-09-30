package de.m00e.whitebox.components;

import de.m00e.whitebox.WhiteBoxMain;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class PomodoroTimer extends BorderPane implements Runnable {

    private final double WIDTH = WhiteBoxMain.getWidth();
    private final double NODE_HEIGHT = WhiteBoxMain.getNodeHeight()+50;
    private final double BUTTON_WIDTH = WhiteBoxMain.getButtonWidth();
    private final double BUTTON_HEIGHT = WhiteBoxMain.getButtonHeight();
    private final double HEIGHT = 400; // Height of the whole timer pane

    private Label timerLabel, statusLabel; // Status label shows whether session nr. ? or breaks running currently.

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

    @Override
    public void run() {

    }
}
