import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

class Panelem extends JFrame {
    private JButton button;
    JPanel MainF;

    public Panelem(BufferedImage img) {
        MainF = new JPanel();
        button = new JButton("Click Me");
        MainF.add(button);
        setContentPane(MainF);
        setTitle("Difference of Gaussian");
        setSize(800,1000);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "FirstPicturedone");
                if(img!=null) display(img);
            }
        });
        setContentPane(MainF);
    }

    private void display(BufferedImage img ) {
        JFrame frame = new JFrame();
        frame.setSize(img.getWidth(), img.getHeight());
        frame.setTitle("Kezdokep");
        frame.setVisible(true);
        frame.add(new JLabel(new ImageIcon(img)));
    }

    /*class ImagePanel extends JPanel {

        private BufferedImage image;
        public ImagePanel(BufferedImage bemenet) {
            image = bemenet;
            int width = 200;
            int height = 200;
            Graphics2D g2d = image.createGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);
            g2d.setColor(Color.RED);
            g2d.drawLine(0, 0, width, height);
            g2d.drawLine(0, height, width, 0);
            g2d.dispose();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Kirajzoljuk a BufferedImage-t a panelre
            g.drawImage(image, 0, 0, this);
        }

    }*/
}
