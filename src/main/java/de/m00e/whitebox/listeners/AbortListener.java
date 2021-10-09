package de.m00e.whitebox.listeners;

import de.m00e.whitebox.components.PomodoroBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AbortListener implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        PomodoroBox.abortTimer();
    }
}
