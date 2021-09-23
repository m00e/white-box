package de.m00e.whitebox.components;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class LabeledTextField extends BorderPane {

    private Label lbl;
    private TextInputControl inputField;

    public LabeledTextField(String lblName, Mode mode) {
        lbl = new Label(lblName);

        switch(mode) {
            case TEXTFIELD:
                inputField = new TextField();
                break;
            case PASSWORD_FIELD:
                inputField = new PasswordField();
                break;
            case TEXTAREA:
                inputField = new TextArea();
                break;
        }

        this.setTop(lbl);
        this.setCenter(inputField);
    }

    public enum Mode {
        TEXTFIELD,
        PASSWORD_FIELD,
        TEXTAREA;
    }
}
