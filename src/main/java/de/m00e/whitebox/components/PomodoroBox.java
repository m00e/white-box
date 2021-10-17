package de.m00e.whitebox.components;

import de.m00e.whitebox.WhiteBoxApp;
import de.m00e.whitebox.listeners.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PomodoroBox extends VBox {

    private final double BUTTON_WIDTH = WhiteBoxApp.getButtonWidth();
    private final double BUTTON_HEIGHT = WhiteBoxApp.getButtonHeight();

    private static Button startBtn, abortBtn; //Start button is also stop button
    private static Hyperlink hyperLink;
    private static LabeledComboBox sessionTimeBox, smallBreakTimeBox, longBreakTimeBox;
    private static ImagePane imgPane;
    private VBox controlPane;
    private static PomodoroWindow pomodoroWindow;

    private static boolean hasStarted, running;

    public PomodoroBox() {
        hasStarted = false;

        setupComponents();

        getChildren().addAll(imgPane, hyperLink, controlPane);
        this.setAlignment(Pos.TOP_CENTER);
        setSpacing(10);
        setPadding(new Insets(0,10,10,10));
    }

    private void setupComponents() {
        imgPane = new ImagePane("/icons/pomodoro_icon.png"); // Load main icon

        hyperLink = new Hyperlink("> Wikipedia: Pomodoro Technique <");
        hyperLink.setFont(new Font("Arial", 16));
        hyperLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://en.wikipedia.org/wiki/Pomodoro_Technique"));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        sessionTimeBox = new LabeledComboBox("Session-Time:", 20,60);
        smallBreakTimeBox = new LabeledComboBox("Small Breaks:", 5,15);
        longBreakTimeBox = new LabeledComboBox("Long Breaks:", 15,60);

        startBtn = new Button("Start");
        startBtn.getStyleClass().add("button-glassgrey");
        startBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        startBtn.setOnAction(new StartStopListener());

        abortBtn = new Button("Abort");
        abortBtn.getStyleClass().add("button-glassgrey");
        abortBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        abortBtn.setDisable(true); // Abort button shouldn't be usable if timer hasn't started yet.
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
        abortBtn.setDisable(false);
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
        abortBtn.setDisable(true);
    }

    public static void setRunning(boolean run) {
        running = run;
    }

    public static Button getStartBtn() { return startBtn; }
    public static PomodoroWindow getPomodoroWindow() { return pomodoroWindow;}
    public static boolean isRunning() { return running; }
    public static boolean isHasStarted() {
        return hasStarted;
    }
}
