package de.m00e.whitebox.components;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class LabeledComboBox extends BorderPane {

    private Label lbl;
    private ComboBox<Integer> comboBox;

    public LabeledComboBox(String lblName, int minTime, int maxTime) {
        lbl = new Label(lblName);
        comboBox = new ComboBox<>();

        for(int i = minTime; i <= maxTime; i+=5) {
            comboBox.getItems().add(i);
        }

        this.setCenter(lbl);
        this.setRight(comboBox);
    }
}
