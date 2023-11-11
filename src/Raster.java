import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;

public class Raster {
    int width;  //kép mérete
    int height; //kép mérete
    Cell[][] rows; //kép pixelei
    int mGauss; //filter mérete
    public Raster(int height, int width) {
        this.width = width;
        this.height = height;
        this.rows = new Cell[height][width];
    }

    public void set(int x, int y, float z) {
        if (x >= 0 && x < width && y >= 0 && y < height)
            rows[y][x] = new Cell(z, 0.0f);
    }

    public Cell get(int x, int y) {
        if(x >= 0 && x < width && y >= 0 && y < height)
            return rows[y][x];
        else return null;
    }

    public Raster(BufferedImage original) throws IOException {
// Létrehozni egy Raster objektumot
        this.width = original.getWidth();
        this.height = original.getHeight();
        this.rows = new Cell[height][width];
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                rows[y][x] = new Cell(original.getRaster().getSample(y,x,0), 0.0f);
            }
        }

    }



    public static BufferedImage toImage(Raster raster) {
        BufferedImage image = new BufferedImage(raster.width, raster.height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < raster.height; y++) {
            for (int x = 0; x < raster.width; x++) {
                image.getRaster().setSample(x, y, 0, raster.get(y, x).z);
            }
        }


        return image;
    }
}
