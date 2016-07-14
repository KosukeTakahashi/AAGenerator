import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by 孝輔 on 2016/07/14.
 */
public class ControlPanel extends JFrame {
    private JPanel panel;
    private JProgressBar progressBar;
    private JTextField editFilePath;
    private JTextField editStringToUse;
    private JButton btnShowDlg;
    private JButton btnClear;
    private JButton btnGenerate;
    private JSlider accuracySlider;
    private JSlider fontSizeSlider;
    private JButton btnCancel;

    private static String filePath = "img/ISAF.gif";
    private static String stringToUse = "連合軍ＩＳＡＦ・・　";
    private static int FONT_SIZE = 4;
    private static int ACCURACY = 2;

    static Output out;
    static Preview preview;

    public static void main(String[] args) {
        new ControlPanel();
        preview = new Preview();
        out = new Output();
    }

    public ControlPanel() {
        add(panel);
        setSize(500, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("コントロールパネル");
        setVisible(true);

        accuracySlider.setMinimum(1);
        accuracySlider.setMaximum(5);
        accuracySlider.setValue(ACCURACY);

        fontSizeSlider.setMinimum(1);
        fontSizeSlider.setMaximum(20);
        fontSizeSlider.setValue(FONT_SIZE);

        editFilePath.setText(filePath);

        editStringToUse.setText(stringToUse);

        btnShowDlg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG & PNG & GIF Images", "jpg", "png", "gif");
                chooser.setFileFilter(filter);
                int returnValue = chooser.showOpenDialog(ControlPanel.this);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    filePath = chooser.getSelectedFile().getPath();
                    editFilePath.setText(filePath);
                    preview.setIcon(filePath);
                } else {
                    System.out.println("Error or Canceled.");
                }
            }
        });
        btnClear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                out.clear();
            }
        });
        btnGenerate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                FONT_SIZE = fontSizeSlider.getValue();
                ACCURACY = accuracySlider.getValue();
                stringToUse = editStringToUse.getText();
                filePath = editFilePath.getText();

                out.setFONT_SIZE(FONT_SIZE);
                out.setACCURACY(ACCURACY);
                out.setFilePath(filePath);
                out.setStringToUse(stringToUse);

                new Thread(out).start();
            }
        });
        btnCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                out.isActive = false;
            }
        });
    }

    public ControlPanel(boolean visible) {
        if (visible) new ControlPanel();
    }

    public void setBtnsEnabled(boolean enabled) {
        btnClear.setEnabled(enabled);
        btnGenerate.setEnabled(enabled);
    }
}