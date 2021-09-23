package de.m00e.whitebox.listeners;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class OpenURLButtonListener implements EventHandler<ActionEvent> {

    private String url;
    private String username;
    private String password;

    public OpenURLButtonListener(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public void handle(ActionEvent event) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
