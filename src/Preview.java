import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by 孝輔 on 2016/07/14.
 */
public class Preview extends JFrame {
    private String filePath = "img/ISAF.gif";
    private ImageIcon icon = new ImageIcon(filePath);
    private JLabel label = new JLabel(icon);

    public Preview() {
        setImage();

        add(new JScrollPane(label));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(0, 200, 500, 530);
        setTitle("プレビュー");
        setVisible(true);
    }

    public void setImage() {
        ImageIcon raw = new ImageIcon(filePath);
        BufferedImage img;
        Image resizedImg;

        try {
            img = ImageIO.read(new File(filePath));

            int w = raw.getIconWidth();
            int h = raw.getIconHeight();

            if (w > h) {
                //横長
                int resized = h / (w / 500);
                resizedImg = img.getScaledInstance(500, resized, img.SCALE_SMOOTH);
                icon = new ImageIcon(resizedImg);

            } else {
                //縦長
                int resized = w / (h / 500);
                resizedImg = img.getScaledInstance(resized, 500, img.SCALE_SMOOTH);
                icon = new ImageIcon(resizedImg);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        label.setIcon(icon);
    }

    public void setIcon(String path) {
        filePath = path;
        icon = new ImageIcon(path);
        setImage();
    }
}
