
import javax.swing.*;
        import java.awt.*;
        import java.io.File;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        BufferedImage originalImage = null;
        BufferedImage outimage=null;
        try {
            // Load the image from the source file
            File sourceImageFile = new File("test.png");

            originalImage = ImageIO.read(sourceImageFile);



            Raster rasteredimage = new Raster(originalImage);

            Raster blur1 = DifferenceOfGaussian.GaussBlur(rasteredimage, 1);
            Raster blur2 = DifferenceOfGaussian.GaussBlur(rasteredimage, 2);
            Raster gauss = DifferenceOfGaussian.DifferenceOfBruls(blur1, blur2);
            outimage = Raster.toImage(gauss);
            ImageIO.write(outimage, "png", new File("kesz.png"));
            System.out.println("Image format conversion completed.");
        } catch (IOException e) {
            e.printStackTrace();
        }




        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Difference of Gaussians Edge Enhancement");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());



            // Create the dropdown menu
            String[] specimens = {"test.png", "test2.png", "kesz.png"};
            JComboBox<String> specimenDropdown = new JComboBox<>(specimens);
            specimenDropdown.addActionListener(e -> {
                String selectedSpecimen = (String) specimenDropdown.getSelectedItem();
                System.out.println("Selected specimen: " + selectedSpecimen);
            });
            frame.add(specimenDropdown, BorderLayout.CENTER);





            // Create the sliders
            JSlider slider1 = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
            slider1.addChangeListener(e -> System.out.println("Slider 1 value: " + slider1.getValue() / 100.0f));
            frame.add(slider1, BorderLayout.SOUTH);

            JSlider slider2 = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
            slider2.addChangeListener(e -> System.out.println("Slider 2 value: " + slider2.getValue() / 100.0f));
            frame.add(slider2, BorderLayout.SOUTH);

            JPanel imagePanel = new JPanel();
            imagePanel.setLayout(new GridLayout(1, 3));
            imagePanel.add(new JLabel(new ImageIcon("test.png")));
            imagePanel.add(new JLabel(new ImageIcon("test2.png")));
            imagePanel.add(new JLabel(new ImageIcon("kesz.png")));
            frame.add(imagePanel, BorderLayout.NORTH);

            frame.pack();
            frame.setVisible(true);
        });
    }
}


