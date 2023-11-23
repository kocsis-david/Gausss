package Junittests;
import Algoritmus.BufferFunctions;
import org.junit.jupiter.api.Test;

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
        int width=img.getWidth();
        int height=img.getHeight();
        img=BufferFunctions.reSizer(img);
        assertEquals(width*(600.000/width), img.getWidth());
    }
}