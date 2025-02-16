package com.example.projetojavafxjdbc.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageConverter {
    public static byte[] imageViewToByteArray(ImageView imageView, String format) {
        Image image = imageView.getImage();
        if (image == null) {
            return null;
        }

        // Criar uma imagem gravável para garantir que temos os pixels
        WritableImage writableImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelReader pixelReader = image.getPixelReader();
        if (pixelReader != null) {
            writableImage.getPixelWriter().setPixels(0, 0, (int) image.getWidth(), (int) image.getHeight(), pixelReader, 0, 0);
        }

        // Converter para BufferedImage sem usar SwingFXUtils
        BufferedImage bufferedImage = new BufferedImage((int) image.getWidth(), (int) image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                bufferedImage.setRGB(x, y, writableImage.getPixelReader().getArgb(x, y));
            }
        }

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, format, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
