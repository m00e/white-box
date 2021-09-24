package de.m00e.whitebox.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class ImagePane extends BorderPane {

    private ImageView imageView;
    private Image image;

    public ImagePane(String path) {
        image = new Image(getClass().getResourceAsStream(path));
        imageView = new ImageView(image);
        this.setCenter(imageView);
    }
}
