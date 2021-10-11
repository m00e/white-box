package de.m00e.whitebox.components;

import de.m00e.whitebox.WhiteBoxMain;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class LabeledComboBox extends BorderPane {

    private final ComboBox<Integer> comboBox;

    public LabeledComboBox(String lblName, int minTime, int maxTime) {
        double PREF_WIDTH = 100;
        Label lbl = new Label(lblName);
        lbl.setStyle(WhiteBoxMain.getDefaultStyle("black"));

        comboBox = new ComboBox<>();
        for(int i = minTime; i <= maxTime; i+=5) {
            comboBox.getItems().add(i);
        }
        comboBox.getSelectionModel().selectFirst();
        comboBox.setPrefWidth(PREF_WIDTH);
        comboBox.getStyleClass().add("combobox");

        this.setCenter(lbl);
        this.setRight(comboBox);
    }

    public ComboBox<Integer> getComboBox() {return comboBox;}
}
