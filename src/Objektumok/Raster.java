package Objektumok;
import Algoritmus.BufferFunctions;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Raster {
   public int width;  //kép mérete
   public int height; //kép mérete
   public Cell[][] rows; //kép pixelei
    public Raster(int height, int width) {
        this.width = width;
        this.height = height;
        this.rows = new Cell[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x <width; x++) {
                rows[y][x] = new Cell(0, 0.0f);
            }
        }
    }

    public void set(int x, int y, float z) {
        if (x >= 0 && x < width && y >= 0 && y < height)
            rows[y][x].z = z;
    }

    public Cell get(int x, int y) {
        if(x >= 0 && x < width && y >= 0 && y < height)
            return rows[y][x];
        else return null;
    }

    public Raster(BufferedImage original) throws IOException {
// Létrehozni egy Objektumok.Raster objektumot
        this.width = original.getWidth();
        this.height = original.getHeight();
        this.rows = new Cell[height][width];
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                rows[y][x] = new Cell(original.getRaster().getSample(x,y,0), 0.0f);
            }

        }

    }



    public static BufferedImage toImage(Raster raster) {
        BufferedImage image = new BufferedImage(raster.width, raster.height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < raster.height; y++) {
            for (int x = 0; x < raster.width; x++) {
                image.getRaster().setSample(x, y, 0, raster.get(x, y).z);
            }
        }
        return image;
    }
}
