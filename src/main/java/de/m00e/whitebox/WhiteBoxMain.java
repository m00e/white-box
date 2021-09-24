package de.m00e.whitebox;

import de.m00e.whitebox.components.ImagePane;
import de.m00e.whitebox.components.LinkBox;
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

    private Stage stage;

    private static final double WIDTH = 850;
    private static final double NODE_HEIGHT = 50;
    private static final double BUTTON_WIDTH = WIDTH/2-20;
    private static final double BUTTON_HEIGHT = NODE_HEIGHT;
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private ImagePane imgPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("White Box");
        setStageBoundsAndLocation(WIDTH, WIDTH, screenSize.height*0.75, screenSize.height*0.75);

        imgPane = new ImagePane("/whitebox_icon.png");
        imgPane.setMaxWidth(WIDTH);
        imgPane.setMaxHeight(125);

        BorderPane rootPane = new BorderPane();
        HBox hBox = new HBox();
        LinkBox linkBox = new LinkBox();
        TaskBox taskBox = new TaskBox();

        hBox.getChildren().addAll(linkBox, new Separator(), taskBox);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(0,10,10,10));

        // Add nodes to window and make it visible
        rootPane.setTop(imgPane);
        rootPane.setCenter(hBox);
        Scene scene = new Scene(rootPane, WIDTH, screenSize.height);
        stage.setScene(scene);
        stage.show();
    }

    private void setStageBoundsAndLocation(double minWidth, double maxWidth, double minHeight, double maxHeight) {
        stage.setMinWidth(minWidth);
        stage.setMaxWidth(minWidth);
        stage.setMinHeight(minHeight);
        stage.setMaxHeight(maxHeight);
    }

    public static double getWidth() {
        return WIDTH;
    }

    public static double getNodeHeight() {
        return NODE_HEIGHT;
    }

    public static String getDefaultButtonStyle() {
        return "-fx-text-fill: black; -fx-font-size: 16px";
    }

    public static double getButtonWidth() {
        return BUTTON_WIDTH;
    }

    public static double getButtonHeight() {
        return BUTTON_HEIGHT;
    }

}
