package Algoritmus;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BufferFunctions {
    public static BufferedImage toGrayScale (BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = img.getRGB(x, y);

                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                // A kép minden pixelének RGB értékeit átszámítjuk egy szürkeárnyalatú értékre.
                int gray = (r + g + b) / 3;

                grayImage.setRGB(x, y, gray);
            }
        }

        return grayImage;
    }

    public static BufferedImage loadImage(File path) {
        try {
            BufferedImage image = ImageIO.read(path);
            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage reSizer(BufferedImage img){
        double sigm=1;
        if(img.getWidth()<600&&img.getHeight()<600)return img;  //Ha a kép kisebb mint 600x600 akkor nem kell átméretezni
        else if(img.getWidth()>600){
            sigm=600.000/ img.getWidth();
        }
        else{
            sigm=600.000/ img.getHeight();
        }

        Image temp=img.getScaledInstance((int)(img.getWidth()*sigm), (int)(img.getHeight()*sigm), Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage((int)(img.getWidth()*sigm), (int)(img.getHeight()*sigm), BufferedImage.TYPE_INT_ARGB);  //Az átméretezett kép létrehozása
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(temp, 0, 0, null);   //A kép másolása
        g.dispose();
        return resizedImage;
    }

    }



