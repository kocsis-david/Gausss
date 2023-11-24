package Junittests;

import org.junit.jupiter.api.Test;
import Framek.KepekPanel;

import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

class KepekPanelTest {

    @Test
    void resizer() {
        BufferedImage img = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        BufferedImage gaussian2 = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        BufferedImage dogi = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        BufferedImage canny = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);

        // Call the Resizer method
        BufferedImage[] resizedImages = KepekPanel.Resizer(img, gaussian2, dogi, canny);

        // Validate the result
        assertNotNull(resizedImages);
        assertEquals(4, resizedImages.length);

        for (BufferedImage resizedImage : resizedImages) {
            assertNotNull(resizedImage);
            assertTrue(resizedImage.getWidth() <= 400);
            assertTrue(resizedImage.getHeight() <= 400);
        }

        // Test with null images
        BufferedImage[] resizedNullImages =KepekPanel.Resizer(null, null, null, null);

        // Validate the result with null images
        assertNotNull(resizedNullImages);
        assertEquals(4, resizedNullImages.length);

        for (BufferedImage resizedNullImage : resizedNullImages) {
            assertNull(resizedNullImage);
        }
    }
}