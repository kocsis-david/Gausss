package Junittests;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import Objektumok.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class RasterTest {

    @Test
    void set() {
        Raster raster = new Raster(10, 10);
        raster.set(5, 5, 0.5f);
        Cell cell = raster.get(5, 5);
        Assert.assertEquals(0.5f, cell.z, 0.001f);
    }
    @org.junit.Test
    public void testCreateFromBufferedImage() throws IOException {
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        image.setRGB(5, 5, 0x00FF00); // Set pixel (5, 5) to green
        Raster raster = null;
        try {
            raster = new Raster(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Cell cell = raster.get(5, 5);
        Assert.assertEquals(0.0f, cell.z, 0.001f);
    }

    @Test
    void get() {
        Raster raster = new Raster(1, 1);
        raster.set(0, 0, 0.5f);
        Cell cell = raster.get(0, 0);
        Assert.assertEquals(0.5f, cell.z, 0.001f);
    }

    @Test
    void toImage() {
        Raster raster = new Raster(1, 1);
        raster.set(0, 0, 255);
        BufferedImage image = Raster.toImage(raster);
        int greyscale=image.getRaster().getSample(0, 0, 0);
        assertEquals(255, greyscale);

    }
}