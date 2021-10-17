package de.m00e.whitebox.components;

import de.m00e.whitebox.WhiteBoxApp;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class InfoPane extends FlowPane {

    private final static double NODE_HEIGHT = WhiteBoxApp.getNodeHeight()/3;

    private Text dateText, connectionState;
    private Timer timer;

    private SimpleDateFormat format;
    private Date date;

    private Separator[] separators;

    private Socket sock;
    private final InetSocketAddress ADDR = new InetSocketAddress("www.google.com", 80);

    public InfoPane() {
        dateText = new Text();
        dateText.setFont(new Font("Arial", NODE_HEIGHT));
        connectionState = new Text();
        connectionState.setFont(new Font("Arial", NODE_HEIGHT));

        timer = new Timer();
        format = new SimpleDateFormat("dd.MM.yyyy"+" "+"HH:mm");

        separators = new Separator[2];
        separators[0] = new Separator();
        separators[1] = new Separator();
        separators[0].setOrientation(Orientation.VERTICAL);
        separators[1].setOrientation(Orientation.VERTICAL);

        getChildren().addAll(separators[0], dateText, separators[1], connectionState);
        GridPane.setHalignment(dateText, HPos.CENTER);
        GridPane.setHalignment(connectionState, HPos.CENTER);

        setHgap(5);
        runTasks();
    }

    public void runTasks() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Date part
                date = Calendar.getInstance().getTime();
                dateText.setText(format.format(date));

                // Connection part
                sock = new Socket();

                try {
                    sock.connect(ADDR,3000);
                    connectionState.setFill(Color.GREEN);
                    connectionState.setText("Online");
                    sock.close();
                } catch (IOException e) {
                    connectionState.setFill(Color.RED);
                    connectionState.setText("Offline");
                }

            }
        }, 0L, 4000L);
    }
}
