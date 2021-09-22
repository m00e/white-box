package de.m00e.whitebox.components;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class LabeledTextField extends BorderPane {

    private Label lbl;
    private TextField tf;
    private PasswordField pf;

    public LabeledTextField(String lblName, boolean isPasswordField) {
        lbl = new Label(lblName);
        if(isPasswordField) {
            pf = new PasswordField();
            this.setCenter(pf);
        } else {
            tf = new TextField();
            this.setCenter(tf);
        }

        this.setLeft(lbl);
    }
}
