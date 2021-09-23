package de.m00e.whitebox;

import de.m00e.whitebox.components.ImagePane;
import de.m00e.whitebox.components.LabeledTextField;
import de.m00e.whitebox.listeners.OpenURLButtonListener;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class WhiteBoxMain extends Application {

    private Stage stage;

    private final double WIDTH = 300;
    private final double MIN_HEIGHT = 500, MAX_HEIGHT = 1080;
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
        this.stage = stage;
        stage.setTitle("White Box");
        setStageBoundsAndLocation(WIDTH, WIDTH, screenSize.getHeight()-200, MAX_HEIGHT, screenSize.width-WIDTH, 0);

        setupComponents();
        addListeners();

        VBox root = new VBox();
        root.getChildren().addAll(imgPane, btn1, btn2, btn3, ltfLogin, ltfPass, new Separator());
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

        btn1.setMinWidth(WIDTH);
        btn2.setMinWidth(WIDTH);
        btn3.setMinWidth(WIDTH);
        btn1.setMinHeight(50);
        btn2.setMinHeight(50);
        btn3.setMinHeight(50);

        ltfLogin = new LabeledTextField("Login: ", LabeledTextField.Mode.TEXTFIELD);
        ltfPass = new LabeledTextField("Password: ", LabeledTextField.Mode.PASSWORD_FIELD);

        imgPane = new ImagePane("/icon_300x150.png");
        imgPane.setMaxWidth(WIDTH);
        imgPane.setMaxHeight(125);
    }

    public void addListeners() {
        btn1.setOnAction(new OpenURLButtonListener("http://www.tu.berlin", ltfLogin.getAccessibleText(), ltfPass.getAccessibleText()));
        btn2.setOnAction(new OpenURLButtonListener("http://www.isis.tu-berlin.de", ltfLogin.getAccessibleText(), ltfPass.getAccessibleText()));
        btn3.setOnAction(new OpenURLButtonListener("http://www.moseskonto.tu-berlin.de", ltfLogin.getAccessibleText(), ltfPass.getAccessibleText()));
    }

    private void setStageBoundsAndLocation(double minWidth, double maxWidth, double minHeight, double maxHeight, double x, double y) {
        stage.setMinWidth(minWidth);
        stage.setMaxWidth(minWidth);
        stage.setMinHeight(minHeight);
        stage.setMaxHeight(maxHeight);
        stage.setX(x);
        stage.setY(y);
    }
}
