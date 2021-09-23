package de.m00e.whitebox;

import de.m00e.whitebox.components.ImagePane;
import de.m00e.whitebox.components.LinkBox;
import de.m00e.whitebox.components.TaskBox;
import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class WhiteBoxMain extends Application {

    private Stage stage;

    private static final double WIDTH = 320;
    private static final double NODE_HEIGHT = 50;
    private static final double BUTTON_WIDTH = WIDTH-20;
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
        setStageBoundsAndLocation(WIDTH, WIDTH, screenSize.height, screenSize.height,screenSize.width-WIDTH, 0);

        imgPane = new ImagePane("/icon_300x150.png");
        imgPane.setMaxWidth(WIDTH);
        imgPane.setMaxHeight(125);

        VBox root = new VBox();
        LinkBox linkBox = new LinkBox();
        TaskBox taskBox = new TaskBox();

        root.getChildren().addAll(imgPane, new Separator(), linkBox, new Separator(), taskBox);
        root.setSpacing(10);
        root.setPadding(new Insets(0,10,10,10));
        ScrollPane sp = new ScrollPane();
        sp.setContent(root);
        sp.setPannable(true); // it means that the user should be able to pan the viewport by using the mouse.

        Scene scene = new Scene(sp, WIDTH, screenSize.height);
        stage.setScene(scene);
        stage.show();
    }

    private void setStageBoundsAndLocation(double minWidth, double maxWidth, double minHeight, double maxHeight, double x, double y) {
        stage.setMinWidth(minWidth);
        stage.setMaxWidth(minWidth);
        stage.setMinHeight(minHeight);
        stage.setMaxHeight(maxHeight);
        stage.setX(x);
        stage.setY(y);
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
