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
import java.util.LinkedList;
import java.util.Scanner;

public class Panelem extends JFrame {

    private JButton button=new JButton("Run DoG");
    private JLabel jLabel1=new JLabel();
    private JTextField jTextField1 = new JTextField(3);
    private JTextField jTextField2 = new JTextField(3);
    static File folder = new File("testpics");
    public static File[] listOfFiles = folder.listFiles();

    private JComboBox<Object> jComboBox1 = new JComboBox<>(listOfFiles);

    LinkedList<Futtatas> listOfRuns = new LinkedList<>();


    {
        try {
            Scanner scanner = new Scanner(new File("src/eredmenyek.txt"));
            while (scanner.hasNextLine()){
                String[] line = scanner.nextLine().split(" ");
                listOfRuns.add(new Futtatas(new File(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2])));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
           System.out.println("Nincs még nem volt előző futtatás");
        }
    }


    private JComboBox<Object> jComboBox2 = new JComboBox<>(listOfRuns.toArray());

    public Panelem(){


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Difference of gauss setup");
        setSize(1000,800);
        setResizable(true);
        setVisible(true);
        BoxLayout boxLayout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
        JPanel panel = new JPanel(new FlowLayout());
        JPanel panel2 = new JPanel(new FlowLayout());
        panel.setBackground(Color.CYAN);
        panel2.setBackground(Color.CYAN);
        jTextField1.setText("1");
        jTextField2.setText("5");
        jComboBox1.setSelectedIndex(2);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sigma1 = Integer.parseInt(jTextField1.getText());
                int sigma2 = Integer.parseInt(jTextField2.getText());
                File path = (File) jComboBox1.getSelectedItem();
                Futtatas futtatas = new Futtatas(path, sigma1, sigma2);
                listOfRuns.add(futtatas);
                jComboBox2.addItem(futtatas);
                if(listOfRuns.size()==1)panel2.add(jComboBox2);;
                File file = new File("src/eredmenyek.txt");
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(file, true);
                    fileWriter.write(futtatas.toString()+"\n");
                    fileWriter.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                KepekPanel kepekPanel = new KepekPanel(path, sigma1, sigma2);
                kepekPanel.setVisible(true);
            }
        });
        jComboBox1.addItemListener(e -> {
            BufferedImage image = BufferFunctions.loadImage((File) jComboBox1.getSelectedItem());
            BufferedImage grayImage = BufferFunctions.toGrayScale(image);
            grayImage = BufferFunctions.reSizer(grayImage);
            jLabel1.setIcon(new ImageIcon(grayImage));
        });
        BufferedImage image = BufferFunctions.loadImage((File) jComboBox1.getSelectedItem());
        BufferedImage grayImage = BufferFunctions.toGrayScale(image);
        grayImage = BufferFunctions.reSizer(grayImage);
        jLabel1.setIcon(new ImageIcon(grayImage));


        panel.add(jLabel1);
        panel.add(button);
        panel2.add(jComboBox1);
        panel2.add(jTextField1);
        panel2.add(jTextField2);
        if(listOfRuns.size()>0)
        panel2.add(jComboBox2);
        add(panel);
        add(panel2);
        setLayout(boxLayout);
        pack();
    }



}
