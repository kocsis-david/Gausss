package Junittests;

import Algoritmus.DifferenceOfGaussian;
import Objektumok.Raster;


import static org.junit.Assert.assertEquals;

class DifferenceOfGaussianTest {

    @org.junit.jupiter.api.Test
    void cannyEdge() {
        Raster img = new Raster(3, 3);
        img.set(0, 0, 1);
        img.set(0, 1, 1);
        img.set(0, 2, 1);
        img.set(1, 0, 1);
        img.set(1, 1, 1);
        img.set(1, 2, 1);
        img.set(2, 0, 1);
        img.set(2, 1, 1);
        img.set(2, 2, 1);

        DifferenceOfGaussian.CannyEdge(img);

        assertEquals(0, (int) img.get(1, 1).z);
        assertEquals(1, (int) img.get(0, 0).z);
        assertEquals(1, (int) img.get(0, 1).z);
        assertEquals(1, (int) img.get(0, 2).z);
        assertEquals(1, (int) img.get(1, 0).z);
        //same for the rest

    }

    @org.junit.jupiter.api.Test
    void gaussBlur() {
        Raster img = new Raster(10, 10);
        img.set(5, 5, 10);

        DifferenceOfGaussian.GaussBlur(img, 1);
        assertEquals(10, (int) img.get(5, 5).z);
        assertEquals(10, (int) img.get(5, 4).z);
        assertEquals(10, (int) img.get(5, 6).z);
        assertEquals(10, (int) img.get(4, 5).z);
        assertEquals(10, (int) img.get(6, 5).z);
        assertEquals(10, (int) img.get(4, 4).z);
        assertEquals(10, (int) img.get(6, 6).z);
        assertEquals(0, (int) img.get(0, 1).z);

    }

    @org.junit.jupiter.api.Test
    void differenceOfBruls() {
        Raster img = new Raster(3, 3);
        img.set(1, 1, 255);
        Raster img2 = new Raster(3, 3);
        img2.set(1, 1, 255);
        Raster dog = DifferenceOfGaussian.DifferenceOfBruls(img, img2);
        assertEquals(128, (int) dog.get(1, 1).z);
    }
}