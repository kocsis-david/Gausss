package Framek;

import Algoritmus.*;
import Objektumok.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class KepekPanel extends JFrame {
    public KepekPanel(File path, int sigma1, int sigma2) {

        setSize(800, 600);

        BufferedImage image = BufferFunctions.loadImage(path);  // A kép betöltése
        BufferedImage grayImage = BufferFunctions.toGrayScale(image); // A kép szürkeárnyalatosra alakítása
        grayImage = BufferFunctions.reSizer(grayImage); // A kép átméretezése

        Raster blur1=null;
        Raster blur2=null;
        // A képek létrehozása
        try { //Kell két kép a két blurhoz
            blur1 = new Raster(grayImage);
            blur2 = new Raster(grayImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DifferenceOfGaussian.GaussBlur(blur1, sigma1);  // A képek elmosása
        DifferenceOfGaussian.GaussBlur2(blur2, sigma2);
        Raster dog= DifferenceOfGaussian.DifferenceOfBruls(blur1, blur2);  // A két elmosott kép különbségének kiszámítása
        BufferedImage dogi = Raster.toImage(dog);
        BufferedImage gaussian1 = Raster.toImage(blur1);
        BufferedImage gaussian2 = Raster.toImage(blur2);

        DifferenceOfGaussian.CannyEdge(dog);  // A különbségkép éleit kiszámítja
        BufferedImage canny = Raster.toImage(dog);

        // A képek megjelenítése
        JLabel blurkepegy = new JLabel();
        blurkepegy.setIcon(new ImageIcon(gaussian1));
        JLabel blurkepketto = new JLabel();
        blurkepketto.setIcon(new ImageIcon(gaussian2));
        JLabel diffkep = new JLabel();
        diffkep.setIcon(new ImageIcon(dogi));
        JLabel cannykep = new JLabel();
        cannykep.setIcon(new ImageIcon(canny));


        // A mentés gombok létrehozása
        JButton save1 = new JButton("Save blur1");
        JButton save2 = new JButton("Save blur2");
        JButton save3 = new JButton("Save DoG");
        JButton save4 = new JButton("Save Canny");

        // A gombok eseménykezelőinek hozzárendelése
        save1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImageIO.write(gaussian1, "png", new File("outpics/blur1.png"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        save2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImageIO.write(gaussian2, "png", new File("outpics/blur2.png"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        save3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImageIO.write(dogi, "png", new File("outpics/dog.png"));

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        save4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImageIO.write(canny, "png", new File("outpics/canny.png"));

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });


        BoxLayout boxLayout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
        JPanel panel = new JPanel(new FlowLayout());
        JPanel panel2 = new JPanel(new FlowLayout());
        panel.setBackground(Color.CYAN);
        panel2.setBackground(Color.CYAN);

        panel.add(blurkepegy);
        panel.add(blurkepketto);
        panel2.add(save4);
        panel2.add(diffkep);
        panel2.add(cannykep);
        panel.add(save1);
        panel.add(save2);
        panel2.add(save3);
        panel2.add(save4);
        add(panel);
        add(panel2);
        setLayout(boxLayout);
        pack();
    }
}
