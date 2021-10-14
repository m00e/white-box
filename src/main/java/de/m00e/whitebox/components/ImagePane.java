package de.m00e.whitebox.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class ImagePane extends BorderPane {

    private ImageView imageView;
    private Image image;

    public ImagePane(String path) {

        try {
            image = new Image(getClass().getResourceAsStream(path));
            //image = new Image(WhiteBoxMain.class.getResourceAsStream(path));
            imageView = new ImageView(image);
            this.setCenter(imageView);
        } catch(NullPointerException exc) {
            System.out.println(getClass().getResource("/icons/whitebox_icon.png"));
            this.setCenter(null);
        }

    }
}
