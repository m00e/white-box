package de.m00e.whitebox.listeners;

import de.m00e.whitebox.components.PomodoroBox;
import de.m00e.whitebox.components.PomodoroWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class StartStopListener implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        // Only set true the first time the timer has started.
        if(!PomodoroBox.isHasStarted()) {
            PomodoroBox.initStart();
            return;
        }

        if(PomodoroBox.isRunning()) {
            PomodoroBox.getStartBtn().setText("Start");
            PomodoroWindow.getStartBtn().setText("Start");
            PomodoroBox.setRunning(false);
        } else {
            PomodoroBox.getStartBtn().setText("Stop");
            PomodoroWindow.getStartBtn().setText("Stop");
            PomodoroBox.setRunning(true);
        }
    }
}
