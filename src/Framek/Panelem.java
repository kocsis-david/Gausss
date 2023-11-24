package Framek;
import Objektumok.*;
import Algoritmus.BufferFunctions;
import Framek.KepekPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class Panelem extends JFrame {
    private File selectedimage;
    private int sigma1=1;
    private int sigma2=10;
    private JLabel jLabel1=new JLabel();
    static File folder = new File("testpics");
    public static File[] listOfFiles= folder.listFiles();
    public static LinkedList<Futtatas> listOfRuns = new LinkedList<>();

    {
        try {
            Scanner scanner = new Scanner(new File("src/eredmenyek.txt"));
            while (scanner.hasNextLine()){
                String[] line = scanner.nextLine().split(";");
                listOfRuns.add(new Futtatas(new File(line[0]),new File(line[1]),new File(line[2]),new File(line[3]),Integer.parseInt(line[4]),Integer.parseInt(line[5]),line[6]));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
           System.out.println("Nincs még nem volt előző futtatás");
        }
    }



    public Panelem(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Difference of gauss setup");
        setResizable(true);
        setVisible(true);
        selectedimage=listOfFiles[0];
        BufferedImage selimage = BufferFunctions.loadImage(selectedimage);
        selimage = BufferFunctions.reSizer(selimage);
        setSize(selimage.getWidth(),selimage.getHeight());
        addMenu();
        JPanel hatter=new JPanel();
        hatter.setLayout(new BorderLayout());
        jLabel1=new JLabel(new ImageIcon(selimage));
        hatter.add(jLabel1,BorderLayout.CENTER);
        this.add(hatter,BorderLayout.CENTER);
        hatter.setVisible(true);
        pack();
    }

    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu run = new JMenu("Run");
        JMenu file = new JMenu("File");
        JMenu theme = new JMenu("Theme");
        JMenu help = new JMenu("Help");

        JMenuItem runItem = new JMenuItem("Run DoG");
        runItem.addActionListener(e -> {
            KepekPanel kepekPanel = new KepekPanel(selectedimage, sigma1, sigma2);
            kepekPanel.setVisible(true);
            kepekPanel.pack();
        });

        JMenuItem params = new JMenuItem("Params");
        params.addActionListener(e -> {
            JFrame paramsFrame = new JFrame("Params");
            paramsFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            paramsFrame.setSize(400, 400);
            paramsFrame.setResizable(false);
            paramsFrame.setVisible(true);
            JPanel paramsPanel = new JPanel();
            paramsPanel.setLayout(new GridLayout(2, 2));
            JLabel sigma1Label = new JLabel("Sigma1: "+sigma1);
            JLabel sigma2Label = new JLabel("Sigma2: "+sigma2);
           JSlider sigma1Slider = new JSlider(1, 10, 1);
           JSlider sigma2Slider = new JSlider(1, 50, 10);
            sigma1Slider.addChangeListener(e1 -> {
                sigma1 = sigma1Slider.getValue();
                sigma1Label.setText("Sigma1: " + sigma1);
            });
            sigma2Slider.addChangeListener(e1 -> {
                sigma2 = sigma2Slider.getValue();
                sigma2Label.setText("Sigma2: " + sigma2);
            });
            paramsPanel.add(sigma1Label);
            paramsPanel.add(sigma1Slider);
            paramsPanel.add(sigma2Label);
            paramsPanel.add(sigma2Slider);

            paramsFrame.add(paramsPanel);
            paramsFrame.pack();
        });
        run.add(params);


        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if(!selectedFile.getName().endsWith(".jpg")&&!selectedFile.getName().endsWith(".png")){
                    JOptionPane.showMessageDialog(this, "Nem kép fájl");
                    return;
                }

                BufferedImage image = BufferFunctions.loadImage(selectedFile);
                image = BufferFunctions.reSizer(image);
                jLabel1.setIcon(new ImageIcon(image));
                selectedimage = selectedFile;
                pack();
            }
        });

        JMenuItem chooseTest = new JMenuItem("Choose Test");
        chooseTest.addActionListener(e -> {
            JFrame testFrame = new JFrame("Choose From Tests");
            testFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            testFrame.setSize(500, 200);
            testFrame.setResizable(false);
            testFrame.setVisible(true);
            JPanel testPanel = new JPanel();

            JComboBox<File> testFiles = new JComboBox<>(listOfFiles);
            testFiles.addActionListener(e1 -> {
                selectedimage = (File) testFiles.getSelectedItem();
                BufferedImage image = BufferFunctions.loadImage(selectedimage);
                image = BufferFunctions.reSizer(image);
                jLabel1.setIcon(new ImageIcon(image));
                pack();
            });
            testPanel.add(testFiles);
            testFrame.add(testPanel);
            testFrame.pack();
        });
        file.add(chooseTest);

        JMenuItem savedRuns = new JMenuItem("savedRuns");
        savedRuns.addActionListener(e -> {
            JFrame savedRunsFrame = new JFrame("Saved Runs");
            savedRunsFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            savedRunsFrame.setSize(500, 500);
            savedRunsFrame.setResizable(true);
            savedRunsFrame.setVisible(true);
            JPanel savedRunsPanel = new JPanel();

            JComboBox<Futtatas> savedRunsFiles = new JComboBox<>(listOfRuns.toArray(new Futtatas[0]));
            JComboBox<String> savedRunschoose = new JComboBox<>(new String[]{"Blur1", "Blur2", "Dog","Canny"});
            savedRunsFiles.addActionListener(e1 -> {
                Futtatas futtatas = (Futtatas) savedRunsFiles.getSelectedItem();
                String hely="outpics/";
                switch ((String) savedRunschoose.getSelectedItem()) {
                    case "Blur1":
                        hely += futtatas.getBlur1().toString();
                        selectedimage= new File(hely);
                        break;
                    case "Blur2":
                        hely += futtatas.getBlur2().toString();
                        selectedimage = new File(hely);
                        break;
                    case "Dog":
                        hely += futtatas.getDog().toString();
                        selectedimage = new File(hely);
                        break;
                    case "Canny":
                        hely += futtatas.getCanny().toString();
                        selectedimage = new File(hely);
                        break;
                }
                sigma1 = futtatas.getSigma1();
                sigma2 = futtatas.getSigma2();
                JOptionPane.showMessageDialog(null, "Az előző futtatás paraméterei elmentve: " + sigma1 + " " + sigma2);
                jLabel1.setIcon(new ImageIcon(BufferFunctions.loadImage(selectedimage)));
                pack();
            });
            savedRunsPanel.add(savedRunschoose);
            savedRunsPanel.add(savedRunsFiles);
            savedRunsFrame.add(savedRunsPanel);
            savedRunsFrame.pack();
        });
        file.add(savedRuns);



        JMenuItem changeTheme = new JMenuItem("Change Theme");
        changeTheme.addActionListener(e -> {
            JFrame themeFrame = new JFrame("Choose Theme");
            themeFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            themeFrame.setSize(500, 200);
            themeFrame.setResizable(false);
            themeFrame.setVisible(true);
            JPanel themePanel = new JPanel();

            JComboBox<String> themes = new JComboBox<>(new String[]{"Metal", "Nimbus", "CDE/Motif"});
            themes.addActionListener(e1 -> {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    switch ((String) themes.getSelectedItem()) {
                        case "Metal":
                            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                            break;
                        case "Nimbus":
                            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                            break;
                        case "CDE/Motif":
                            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                            break;
                    }
                    SwingUtilities.updateComponentTreeUI(this);
                    SwingUtilities.updateComponentTreeUI(themeFrame);
                    SwingUtilities.updateComponentTreeUI(themePanel);
                    SwingUtilities.updateComponentTreeUI(menuBar);
                    SwingUtilities.updateComponentTreeUI(run);
                    SwingUtilities.updateComponentTreeUI(file);
                    SwingUtilities.updateComponentTreeUI(theme);
                    SwingUtilities.updateComponentTreeUI(help);
                    themeFrame.pack();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            themePanel.add(themes);
            themeFrame.add(themePanel);
            themeFrame.pack();
        });
        theme.add(changeTheme);

        //Segédlet
        JMenuItem javaDox = new JMenuItem("JavaDox");
        javaDox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Megkérdezzük a felhasználót, hogy melyik HTML dokumentumot szeretné megnyitni

                // Ha a felhasználó megadott egy nevet, akkor megpróbáljuk megnyitni a dokumentumot
                    try {
                        // Megnyitjuk a dokumentumot
                        URL url = new URL("https://docs.oracle.com/javase/7/docs/api/");
                        Desktop.getDesktop().browse(url.toURI());
                    } catch (Exception ex) {
                        // Ha valami hiba történik, akkor megjelenítünk egy hibaüzenetet
                        JOptionPane.showMessageDialog(null, "Hiba történt az HTML dokumentum megnyitásakor: " + ex.getMessage());
                    }

            }
        });
        help.add(javaDox);

        JMenuItem githubItem = new JMenuItem("GitHub");
        githubItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Megkérdezzük a felhasználót, hogy melyik HTML dokumentumot szeretné megnyitni

                // Ha a felhasználó megadott egy nevet, akkor megpróbáljuk megnyitni a dokumentumot
                try {
                    // Megnyitjuk a dokumentumot
                    URL url = new URL("https://github.com/kocsis-david/Gausss.git");
                    Desktop.getDesktop().browse(url.toURI());
                } catch (Exception ex) {
                    // Ha valami hiba történik, akkor megjelenítünk egy hibaüzenetet
                    JOptionPane.showMessageDialog(null, "Hiba történt az HTML dokumentum megnyitásakor: " + ex.getMessage());
                }

            }
        });
        help.add(githubItem);


        file.add(openItem);
        run.add(runItem);
        menuBar.add(run);
        menuBar.add(file);
        menuBar.add(theme);
        menuBar.add(help);
        setJMenuBar(menuBar);
    }


}
