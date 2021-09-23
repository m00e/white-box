package de.m00e.whitebox.components;

import de.m00e.whitebox.WhiteBoxMain;
import de.m00e.whitebox.listeners.OpenURLButtonListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class LinkBox extends VBox {

    private final double WIDTH = WhiteBoxMain.getWidth();
    private final double NODE_HEIGHT = WhiteBoxMain.getNodeHeight();
    private final double BUTTON_WIDTH = WhiteBoxMain.getButtonWidth();
    private final double BUTTON_HEIGHT = WhiteBoxMain.getButtonHeight();

    private Button btn1, btn2, btn3;
    private LabeledTextField ltfLogin, ltfPass;
    private Label linksLabel;

    public LinkBox() {
        setupComponents();
        addListeners();

        getChildren().addAll(linksLabel, btn1, btn2, btn3, ltfLogin, ltfPass);
        setSpacing(10);
        setPadding(new Insets(0,10,10,10));
    }

    private void setupComponents() {
        btn1 = new Button("TU Homepage");
        btn1.setStyle(WhiteBoxMain.getDefaultButtonStyle());
        btn2 = new Button("TU ISIS");
        btn2.setStyle(WhiteBoxMain.getDefaultButtonStyle());
        btn3 = new Button("TU Moses");
        btn3.setStyle(WhiteBoxMain.getDefaultButtonStyle());

        btn1.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        btn2.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        btn3.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        ltfLogin = new LabeledTextField("Login: ", LabeledTextField.Mode.TEXTFIELD);
        ltfPass = new LabeledTextField("Password: ", LabeledTextField.Mode.PASSWORD_FIELD);
        ltfLogin.setMaxWidth(BUTTON_WIDTH);
        ltfPass.setMaxWidth(BUTTON_WIDTH);

        linksLabel = new Label("LINKS");
        linksLabel.setPrefHeight(NODE_HEIGHT);
        linksLabel.setPrefWidth(WIDTH-20);
        linksLabel.setAlignment(Pos.CENTER);
        linksLabel.setFont(new Font("Courier New", NODE_HEIGHT));
    }

    public void addListeners() {
        btn1.setOnAction(new OpenURLButtonListener("http://www.tu.berlin", ltfLogin.getAccessibleText(), ltfPass.getAccessibleText()));
        btn2.setOnAction(new OpenURLButtonListener("http://www.isis.tu-berlin.de", ltfLogin.getAccessibleText(), ltfPass.getAccessibleText()));
        btn3.setOnAction(new OpenURLButtonListener("http://www.moseskonto.tu-berlin.de", ltfLogin.getAccessibleText(), ltfPass.getAccessibleText()));
    }
}
