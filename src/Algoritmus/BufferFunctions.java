package Algoritmus;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BufferFunctions {
    public static BufferedImage toGrayScale (BufferedImage img) {
        BufferedImage grayImage = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);  // A kép szürkeárnyalatosra alakítása
        Graphics g = grayImage.getGraphics();
        g.drawImage(img, 0, 0, null);    // A kép másolása
        g.dispose();
        return grayImage;
    }

    public static BufferedImage loadImage(File path) {
        try {
            BufferedImage image = ImageIO.read(path);
            return image;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Nem sikerült a képet betölteni!", "Hiba", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public static BufferedImage reSizer(BufferedImage img){
        double sigm=1;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if(img.getWidth()<screenSize.width&&img.getHeight()<screenSize.height)return img;  //Ha a kép kisebb mint 600x600 akkor nem kell átméretezni
        else if(img.getWidth()>img.getHeight()){
            sigm=screenSize.width*1.000/ img.getWidth();
        }
        else{
            sigm=screenSize.height*1.000/ img.getHeight();
        }

        Image temp=img.getScaledInstance((int)(img.getWidth()*sigm), (int)(img.getHeight()*sigm), Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage((int)(img.getWidth()*sigm), (int)(img.getHeight()*sigm), BufferedImage.TYPE_INT_ARGB);  //Az átméretezett kép létrehozása
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(temp, 0, 0, null);   //A kép másolása
        g.dispose();
        return resizedImage;
    }

    }



