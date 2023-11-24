package Junittests;
import Algoritmus.BufferFunctions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class BufferFunctionsTest {


    @Test
    void loadImage() {
        BufferedImage img=BufferFunctions.loadImage(new java.io.File("testpics/3x3.png"));
        assertEquals(10, img.getWidth());
        assertEquals(10, img.getHeight());
    }

    @Test
    void reSizer() {
        BufferedImage img=BufferFunctions.loadImage(new java.io.File("testpics/erdo.jpg"));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width=img.getWidth();
        int height=img.getHeight();
        double sigm=screenSize.width*1.000/ img.getWidth();
        img=BufferFunctions.reSizer(img);
        assertEquals(width*(sigm), img.getWidth());
        assertEquals(height*(sigm), img.getHeight());
    }

    @Test
    void toGrayScale() {
        BufferedImage originalImage=BufferFunctions.loadImage(new java.io.File("testpics/3x3.png"));


        // Convert the image to grayscale using the toGrayScale method
        BufferedImage grayImage = BufferFunctions.toGrayScale(originalImage);

        // Assert that the gray image has the same width and height as the original image
        assertEquals(originalImage.getWidth(), grayImage.getWidth());
        assertEquals(originalImage.getHeight(), grayImage.getHeight());

        // Assert that all pixels in the gray image have the same color
        for (int i = 0; i < grayImage.getWidth(); i++) {
            for (int j = 0; j < grayImage.getHeight(); j++) {
                int color = grayImage.getRGB(i, j);
                int red = (color >> 16) & 0xff;
                int green = (color >> 8) & 0xff;
                int blue = color & 0xff;

                assertEquals(red, green);
                assertEquals(green, blue);
            }
        }
    }
}