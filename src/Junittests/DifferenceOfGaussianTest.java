package Junittests;

import Algoritmus.DifferenceOfGaussian;
import Objektumok.Raster;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

class DifferenceOfGaussianTest {

    @org.junit.jupiter.api.Test
    void cannyEdge() {
        Raster img = new Raster(3, 3);
        img.set(0, 0, 0);
        img.set(0, 1, 0);
        img.set(0, 2, 0);
        img.set(1, 0, 0);
        img.set(1, 1, 0);
        img.set(1, 2, 0);
        img.set(2, 0, 0);
        img.set(2, 1, 0);
        img.set(2, 2, 0);

        DifferenceOfGaussian.CannyEdge(img);

        assertEquals(0, (int) img.get(1, 1).z);


    }

    @org.junit.jupiter.api.Test
    void gaussBlur() {
        Raster img = new Raster(10, 10);
        img.set(5, 5, 20);
        img.set(5, 6, 80);
        img.set(5, 7, 100);
        DifferenceOfGaussian.GaussBlur(img, 1);
        try {
            ImageIO.write(Raster.toImage(img), "png", new File("testpics/3x3.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @org.junit.jupiter.api.Test
    void differenceOfBruls() {
        Raster img = new Raster(3, 3);
        Raster img2 =new Raster(3, 3);
        img.set(1, 1, 255);
        img2.set(1, 1, 1);//blurrolni kell
        Raster dog = DifferenceOfGaussian.DifferenceOfBruls(img, img2);
        assertEquals(254, (int) dog.get(1, 1).z);

    }
}