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
        setTitle("Képek");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
        setSize(screenSize.width, screenSize.height);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(blurkepegy);
        panel.add(blurkepketto);
        panel.add(diffkep);
        panel.add(cannykep);
        JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(screenSize.width, screenSize.height));

        add(scrollPane);
        pack();
    }
}
