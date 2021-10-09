package de.m00e.whitebox;

import de.m00e.whitebox.components.ImagePane;
import de.m00e.whitebox.components.PomodoroBox;
import de.m00e.whitebox.components.TaskBox;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.*;

public class WhiteBoxMain extends Application {

    private static final double WIDTH = 850;
    private static final double NODE_HEIGHT = 50;
    private static final double BUTTON_WIDTH = WIDTH/2-20;
    private static final double BUTTON_HEIGHT = NODE_HEIGHT;
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("White Box");
        stage.setResizable(false);
        stage.setWidth(WIDTH);
        stage.setHeight(screenSize.getHeight()*0.75);

        ImagePane imgPane = new ImagePane("/whitebox_icon.png"); // Load main icon
        imgPane.setMaxWidth(WIDTH);
        imgPane.setMaxHeight(125);

        BorderPane rootPane = new BorderPane();
        HBox hBox = new HBox();
        PomodoroBox pomodoroBox = new PomodoroBox();
        TaskBox taskBox = new TaskBox();

        hBox.getChildren().addAll(pomodoroBox, new Separator(), taskBox);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(0,10,10,10));

        // Add nodes to window and make it visible
        rootPane.setTop(imgPane);
        rootPane.setCenter(hBox);
        Scene scene = new Scene(rootPane, WIDTH, screenSize.height);

        stage.setOnCloseRequest(event -> {
            if(PomodoroBox.getPomodoroWindow() != null) {
                PomodoroBox.abortTimer();
                PomodoroBox.getPomodoroWindow().getTimerStage().close();
            }
            stage.close();
        });
        stage.setScene(scene);
        stage.show();
    }

    public static double getWidth() {
        return WIDTH;
    }
    public static double getNodeHeight() {
        return NODE_HEIGHT;
    }
    public static double getButtonWidth() {
        return BUTTON_WIDTH;
    }
    public static double getButtonHeight() {
        return BUTTON_HEIGHT;
    }

    public static String getDefaultStyle(String textColor) {
        return "-fx-text-fill: " + textColor + "; -fx-font-size: 16px;";
    }

    public static String getDefaultStyle(String textColor, String backgroundColor) {
        return "-fx-text-fill: " + textColor + "; -fx-background-color: " + backgroundColor + "; -fx-font-size: 16px;";
    }

    public static String getGlassGreyStyle() {
        return "-fx-background-color: \n" +
                "#c3c4c4,\n" +
                "linear-gradient(#d6d6d6 50%, white 100%),\n" +
                "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\n" +
                "-fx-background-radius: 30;\n" +
                "-fx-background-insets: 0,1,1;\n" +
                "-fx-text-fill: black;\n" +
                "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );";
    }
}
