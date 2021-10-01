package de.m00e.whitebox.components;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class LabeledComboBox extends BorderPane {

    private final double PREF_WIDTH = 100;
    private Label lbl;
    private ComboBox<Integer> comboBox;

    public LabeledComboBox(String lblName, int minTime, int maxTime) {
        lbl = new Label(lblName);
        lbl.setStyle("-fx-text-fill: black; -fx-font-size: 16px;");

        comboBox = new ComboBox<>();
        for(int i = minTime; i <= maxTime; i+=5) {
            comboBox.getItems().add(i);
        }
        comboBox.getSelectionModel().selectFirst();
        comboBox.setPrefWidth(PREF_WIDTH);

        this.setCenter(lbl);
        this.setRight(comboBox);
    }

    public ComboBox<Integer> getComboBox() {return comboBox;}
}
