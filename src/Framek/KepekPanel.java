package Framek;

import Algoritmus.*;
import Objektumok.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.GapContent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

public class KepekPanel extends JFrame {
    BufferedImage gaussian1;
    BufferedImage gaussian2;
    BufferedImage dogi;
    BufferedImage canny;
    public KepekPanel(File path, int sigma1, int sigma2) {
        setTitle("Elkészített Képek");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        BufferedImage image = BufferFunctions.loadImage(path);  // A kép betöltése
        BufferedImage grayImage = BufferFunctions.toGrayScale(image); // A kép szürkeárnyalatosra alakítása
        grayImage = BufferFunctions.reSizer(grayImage); // A kép átméretezése
        setSize(screenSize.width, grayImage.getHeight());
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
        blurkepegy.setText("<-Blur1 ");
        blurkepketto.setText("<-Blur2 ");
        diffkep.setText("<-Difference of Gaussians ");
        cannykep.setText("<-Canny Edge ");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(blurkepegy);

        panel.add(blurkepketto);

        panel.add(diffkep);

        panel.add(cannykep);
        panel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panel.setBackground(Color.getHSBColor(0.5f, 0.5f, 0.5f));
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
                    String pathname = path.getName().substring(0, path.getName().length() - 4);

                    // Write images to PNG files
                    ImageIO.write(gaussian1, "png", new File("outpics/" + pathname + "b1" + ".png"));
                    ImageIO.write(gaussian2, "png", new File("outpics/" + pathname + "b2" + ".png"));
                    ImageIO.write(dogi, "png", new File("outpics/" + pathname + "dog" + ".png"));
                    ImageIO.write(canny, "png", new File("outpics/" + pathname + "canny" + ".png"));

                    // Create Futtatas object with serialized file paths
                    Futtatas futtatas = new Futtatas(pathname + "b1" + ".png",
                            pathname + "b2" + ".png", pathname + "dog" + ".png",
                           pathname + "canny" + ".png", sigma1, sigma2, new java.util.Date().toString());

                    // Add Futtatas object to list of runs
                    Panelem.listOfRuns.add(futtatas);


                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                JOptionPane.showMessageDialog(null, "Elmentve!");

            }
        });
        ment.add(kepmentese);

        JMenuItem galeria = new JMenuItem("Eddigi futtatások");
        galeria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JFrame frame = new JFrame("Eddigi futtatások");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        JPanel ujpanel = new JPanel(new GridLayout(Panelem.listOfRuns.size(), 1));
                        ujpanel.setBackground(Color.black);
                        float i = 0.5f;
                        for (Futtatas futtatas : Panelem.listOfRuns) {
                            String nev = futtatas.getBlur1().substring(8, futtatas.getBlur1().length()-6);
                            JLabel currentkepnev = new JLabel(" "+nev.toUpperCase()+": ");
                            BufferedImage gaussian1 = BufferFunctions.loadImage(new File(futtatas.getBlur1()));
                            BufferedImage gaussian2 = BufferFunctions.loadImage(new File(futtatas.getBlur2()));
                            BufferedImage dogi = BufferFunctions.loadImage(new File(futtatas.getDog()));
                            BufferedImage canny = BufferFunctions.loadImage(new File(futtatas.getCanny()));
                            if(gaussian1==null||gaussian2==null||dogi==null||canny==null){
                                JOptionPane.showMessageDialog(null, "Nem sikerült a képet betölteni!", nev, JOptionPane.ERROR_MESSAGE);
                                continue;
                            }
                            BufferedImage[] resized = Resizer(gaussian1, gaussian2, dogi, canny);
                            JLabel space1 = new JLabel(" ");
                            JLabel space2 = new JLabel(" ");
                            JLabel space3 = new JLabel(" ");
                            JLabel currentblurkepegy = new JLabel();
                            currentblurkepegy.setIcon(new ImageIcon(resized[0]));
                            JLabel currentblurkepketto = new JLabel();
                            currentblurkepketto.setIcon(new ImageIcon(resized[1]));
                            JLabel currentdiffkep = new JLabel();
                            currentdiffkep.setIcon(new ImageIcon(resized[2]));
                            JLabel currentcannykep = new JLabel();
                            currentcannykep.setIcon(new ImageIcon(resized[3]));

                            JPanel panel = new JPanel();
                            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
                            panel.setBackground(Color.getHSBColor(i, i, i));
                            i+=0.1f;
                            if(i>1)i=0.5f;

                            panel.add(currentkepnev);
                            panel.add(currentblurkepegy);
                            panel.add(space1);
                            panel.add(currentblurkepketto);
                            panel.add(space2);
                            panel.add(currentdiffkep);
                            panel.add(space3);
                            panel.add(currentcannykep);
                            ujpanel.add(panel);
                        }

                        JScrollPane scrollPane = new JScrollPane(ujpanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                        frame.add(scrollPane);
                        frame.pack();
                        frame.setVisible(true);
                    }
                });
            }
        });
        Galeria.add(galeria);








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
    public static BufferedImage[] Resizer(BufferedImage img, BufferedImage gaussian2, BufferedImage dogi, BufferedImage canny) {
        BufferedImage[] temp = new BufferedImage[4];
        temp[0] = img;
        temp[1] = gaussian2;
        temp[2] = dogi;
        temp[3] = canny;

        double sigm = 1;

        if (img == null || gaussian2 == null || dogi == null || canny == null) {
            // Handle null images
            return temp;
        }

        if (img.getWidth() < 400 && img.getHeight() < 400) {
            // If the image is smaller than 600x600, no need to resize
            return temp;
        } else if (img.getWidth() > img.getHeight()) {
            sigm = 400.0 / img.getWidth();
        } else {
            sigm = 400.0 / img.getHeight();
        }

        for (int i = 0; i < 4; i++) {
            Image tempi = temp[i].getScaledInstance((int) (temp[i].getWidth() * sigm), (int) (temp[i].getHeight() * sigm), Image.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage((int) (temp[i].getWidth() * sigm), (int) (temp[i].getHeight() * sigm), BufferedImage.TYPE_INT_RGB);

            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(tempi, 0, 0, null);
            g.dispose();
            temp[i] = resizedImage;
        }

        return temp;
    }

}
