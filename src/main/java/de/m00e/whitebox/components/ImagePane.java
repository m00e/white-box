package de.m00e.whitebox.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class ImagePane extends BorderPane {

    public ImagePane(String path) {

        try {
            Image image = new Image(getClass().getResourceAsStream(path));
            ImageView imageView = new ImageView(image);
            this.setCenter(imageView);
        } catch(NullPointerException exc) {
            this.setCenter(null);
        }

    }
}
