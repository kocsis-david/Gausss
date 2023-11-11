import java.awt.*;
import java.awt.image.BufferedImage;

public class BufferFunctions {
    public static BufferedImage toGrayScale (BufferedImage img) {
        BufferedImage grayImage = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayImage.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return grayImage;
    }
    public static BufferedImage blur (BufferedImage img) {
        BufferedImage blurImg = new BufferedImage(
                img.getWidth()-2, img.getHeight()-2, BufferedImage.TYPE_BYTE_GRAY);
        int pix = 0;
        for (int y=0; y<blurImg.getHeight(); y++) {
            for (int x=0; x<blurImg.getWidth(); x++) {
                pix = (int)(4*(img.getRGB(x+1, y+1)& 0xFF)
                        + 2*(img.getRGB(x+1, y)& 0xFF)
                        + 2*(img.getRGB(x+1, y+2)& 0xFF)
                        + 2*(img.getRGB(x, y+1)& 0xFF)
                        + 2*(img.getRGB(x+2, y+1)& 0xFF)
                        + (img.getRGB(x, y)& 0xFF)
                        + (img.getRGB(x, y+2)& 0xFF)
                        + (img.getRGB(x+2, y)& 0xFF)
                        + (img.getRGB(x+2, y+2)& 0xFF))/16;
                int p = (255<<24) | (pix<<16) | (pix<<8) | pix;
                blurImg.setRGB(x,y,p);
            }
        }
        return blurImg;
    }

    // apply 5x5 Gaussian blur to a grayscale image
    public static BufferedImage heavyblur (BufferedImage img) {
        BufferedImage blurImg = new BufferedImage(
                img.getWidth()-4, img.getHeight()-4, BufferedImage.TYPE_BYTE_GRAY);
        int pix = 0;
        for (int y=0; y<blurImg.getHeight(); y++) {
            for (int x=0; x<blurImg.getWidth(); x++) {
                pix = (int)(
                        10*(img.getRGB(x+3, y+3)& 0xFF)
                                + 6*(img.getRGB(x+2, y+1)& 0xFF)
                                + 6*(img.getRGB(x+1, y+2)& 0xFF)
                                + 6*(img.getRGB(x+2, y+3)& 0xFF)
                                + 6*(img.getRGB(x+3, y+2)& 0xFF)
                                + 4*(img.getRGB(x+1, y+1)& 0xFF)
                                + 4*(img.getRGB(x+1, y+3)& 0xFF)
                                + 4*(img.getRGB(x+3, y+1)& 0xFF)
                                + 4*(img.getRGB(x+3, y+3)& 0xFF)
                                + 2*(img.getRGB(x, y+1)& 0xFF)
                                + 2*(img.getRGB(x, y+2)& 0xFF)
                                + 2*(img.getRGB(x, y+3)& 0xFF)
                                + 2*(img.getRGB(x+4, y+1)& 0xFF)
                                + 2*(img.getRGB(x+4, y+2)& 0xFF)
                                + 2*(img.getRGB(x+4, y+3)& 0xFF)
                                + 2*(img.getRGB(x+1, y)& 0xFF)
                                + 2*(img.getRGB(x+2, y)& 0xFF)
                                + 2*(img.getRGB(x+3, y)& 0xFF)
                                + 2*(img.getRGB(x+1, y+4)& 0xFF)
                                + 2*(img.getRGB(x+2, y+4)& 0xFF)
                                + 2*(img.getRGB(x+3, y+4)& 0xFF)
                                + (img.getRGB(x, y)& 0xFF)
                                + (img.getRGB(x, y+2)& 0xFF)
                                + (img.getRGB(x+2, y)& 0xFF)
                                + (img.getRGB(x+2, y+2)& 0xFF))/74;
                int p = (255<<24) | (pix<<16) | (pix<<8) | pix;
                blurImg.setRGB(x,y,p);
            }
        }
        return blurImg;
    }


    public static BufferedImage detectEdges (BufferedImage img) {
        int h = img.getHeight(), w = img.getWidth(), threshold = 30, p = 0;
        BufferedImage edgeImg = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
        int[][] vert = new int[w][h];
        int[][] horiz = new int[w][h];
        int[][] edgeWeight = new int[w][h];
        for (int y = 1; y < h - 1; y++) {
            for (int x = 1; x < w - 1; x++) {
                vert[x][y] = (int) (img.getRGB(x + 1, y - 1) & 0xFF + 2 * (img.getRGB(x + 1, y) & 0xFF) + img.getRGB(x + 1, y + 1) & 0xFF
                        - img.getRGB(x - 1, y - 1) & 0xFF - 2 * (img.getRGB(x - 1, y) & 0xFF) - img.getRGB(x - 1, y + 1) & 0xFF);
                horiz[x][y] = (int) (img.getRGB(x - 1, y + 1) & 0xFF + 2 * (img.getRGB(x, y + 1) & 0xFF) + img.getRGB(x + 1, y + 1) & 0xFF
                        - img.getRGB(x - 1, y - 1) & 0xFF - 2 * (img.getRGB(x, y - 1) & 0xFF) - img.getRGB(x + 1, y - 1) & 0xFF);
                edgeWeight[x][y] = (int) (Math.sqrt(vert[x][y] * vert[x][y] + horiz[x][y] * horiz[x][y]));
                if (edgeWeight[x][y] > threshold)
                    p = (255 << 24) | (255 << 16) | (255 << 8) | 255;
                else
                    p = (255 << 24) | (0 << 16) | (0 << 8) | 0;
                edgeImg.setRGB(x, y, p);
            }
        }
        return edgeImg;
    }

    public static BufferedImage brighten (BufferedImage img, int percentage) {
        int r=0, g=0, b=0, rgb=0, p=0;
        int amount = (int)(percentage * 255 / 100); // rgb scale is 0-255, so 255 is 100%
        BufferedImage newImage = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y=0; y<img.getHeight(); y+=1) {
            for (int x=0; x<img.getWidth(); x+=1) {
                rgb = img.getRGB(x, y);
                r = ((rgb >> 16) & 0xFF) + amount;
                g = ((rgb >> 8) & 0xFF) + amount;
                b = (rgb & 0xFF) + amount;
                if (r>255) r=255;
                if (g>255) g=255;
                if (b>255) b=255;
                p = (255<<24) | (r<<16) | (g<<8) | b;
                newImage.setRGB(x,y,p);
            }
        }
        return newImage;
    }


}
