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
import java.io.FileWriter;
import java.io.IOException;

public class KepekPanel extends JFrame {
    BufferedImage gaussian1;
    BufferedImage gaussian2;
    BufferedImage dogi;
    BufferedImage canny;
    public KepekPanel(File path, int sigma1, int sigma2) {
        setTitle("Képek");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        BufferedImage image = BufferFunctions.loadImage(path);  // A kép betöltése
        BufferedImage grayImage = BufferFunctions.toGrayScale(image); // A kép szürkeárnyalatosra alakítása
        grayImage = BufferFunctions.reSizer(grayImage); // A kép átméretezése
        setSize(grayImage.getWidth(), grayImage.getHeight());
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
        dogi = Raster.toImage(dog);
        gaussian1 = Raster.toImage(blur1);
        gaussian2 = Raster.toImage(blur2);
        DifferenceOfGaussian.CannyEdge(dog);  // A különbségkép éleit kiszámítja
        canny = Raster.toImage(dog);
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
        addMenuBar(path, sigma1, sigma2);
        add(scrollPane);
        pack();
    }

    private void addMenuBar(File path, int sigma1, int sigma2) {
        JMenuBar menuBar = new JMenuBar();
        JMenu ment = new JMenu("Mentés");
        JMenu Galeria = new JMenu("Galeria");
        JMenuItem kepmentese = new JMenuItem("Kép mentése");
        kepmentese.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String pathname=path.getName().substring(0, path.getName().length()-4);
                    ImageIO.write(gaussian1, "png", new File("outpics/"+pathname+"b1"+".png"));
                    ImageIO.write(gaussian2, "png", new File("outpics/"+pathname+"b2"+".png"));
                    ImageIO.write(dogi, "png", new File("outpics/"+pathname+"dog"+".png"));
                    ImageIO.write(canny, "png", new File("outpics/"+pathname+"canny"+".png"));
                    Futtatas futtatas = new Futtatas(new File(pathname+"b1"+".png"), new File(pathname+"b2"+".png"), new File(pathname+"dog"+".png"), new File(pathname+"canny"+".png"), sigma1, sigma2, new java.util.Date().toString());
                    Panelem.listOfRuns.add(futtatas);
                    File file = new File("src/eredmenyek.txt");
                    FileWriter fileWriter = new FileWriter(file, true);
                        fileWriter.write(futtatas.toString()+"\n");
                        fileWriter.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(null, "Elmentve!");

            }
        });
        ment.add(kepmentese);

        JMenu share = new JMenu("Share");
        JMenuItem elonmusktwittere = new JMenuItem("X");
        elonmusktwittere.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "A megosztás funkció még nem elérhető!");
            }
        });
        share.add(elonmusktwittere);





        menuBar.add(ment);
        menuBar.add(Galeria);
        menuBar.add(share);
        setJMenuBar(menuBar);
    }
}
