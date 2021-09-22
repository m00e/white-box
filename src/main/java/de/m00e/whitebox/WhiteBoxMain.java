package de.m00e.whitebox;

import de.m00e.whitebox.components.ImagePane;
import de.m00e.whitebox.components.LabeledTextField;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class WhiteBoxMain extends Application {

    private final int WIDTH = 250;
    private final int MIN_HEIGHT = 500, MAX_HEIGHT = 1080;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    // Components
    private Button btn1, btn2, btn3;
    private LabeledTextField ltfLogin, ltfPass;
    private ImagePane imgPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("White Box");
        stage.setMinWidth(WIDTH);
        stage.setMaxWidth(WIDTH);
        stage.setMinHeight(screenSize.getHeight()-200);
        stage.setMaxHeight(MAX_HEIGHT);
        stage.setX(screenSize.width-WIDTH);
        stage.setY(0);

        setupComponents();

        VBox root = new VBox();
        root.getChildren().addAll(imgPane, btn1, btn2, btn3, ltfLogin, ltfPass);
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        ScrollPane sp = new ScrollPane();
        sp.setContent(root);
        sp.setPannable(true); // it means that the user should be able to pan the viewport by using the mouse.
        Scene scene = new Scene(sp, WIDTH, MIN_HEIGHT);

        stage.setScene(scene);
        stage.show();
    }

    private void setupComponents() {
        btn1 = new Button("TU Homepage");
        btn2 = new Button("TU ISIS");
        btn3 = new Button("TU Moses");

        btn1.setMinWidth(WIDTH-20);
        btn2.setMinWidth(WIDTH-20);
        btn3.setMinWidth(WIDTH-20);

        btn1.setMinHeight(50);
        btn2.setMinHeight(50);
        btn3.setMinHeight(50);

        ltfLogin = new LabeledTextField("Name\t", false);
        ltfPass = new LabeledTextField("Password\t", true);

        imgPane = new ImagePane("file:src/main/resources/images/icon.png");
        imgPane.setMaxWidth(WIDTH);
        imgPane.setMaxHeight(125);
    }
}
