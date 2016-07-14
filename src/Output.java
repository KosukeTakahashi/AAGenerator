import javax.swing.*;
import java.awt.*;
import java.awt.image.PixelGrabber;

/**
 * Created by 孝輔 on 2016/07/14.
 */
public class Output extends JFrame implements Runnable {

    private int FONT_SIZE;
    private int ACCURACY;
    private String stringToUse;
    private String filePath;

    private JTextArea jta = new JTextArea();
    private ImageIcon icon;

    public boolean isActive = true;

    public Output() {
        jta.setFont(new Font("ＭＳ　ゴシック", Font.BOLD, 4));

        add(new JScrollPane(jta));

        setBounds(500, 0, 850, 730);
        setTitle("AA(0% - 待機中)");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void run() {
        icon = new ImageIcon(filePath);
        ControlPanel ctrl = new ControlPanel(false);
        jta.setFont(new Font("M+ 1m", Font.BOLD, FONT_SIZE));
        jta.setText("");

        try {
            ctrl.setBtnsEnabled(false);
            int w = icon.getIconWidth();
            int h = icon.getIconHeight();
            int[] data = new int[w * h];

            PixelGrabber pg = new PixelGrabber(icon.getImage(), 0, 0, w, h, true);

            System.out.println("画像サイズ : " + w + "×" + h);

            if ((w < 0) || (h < 0)) {
                System.err.println("画像の読み込みに失敗：ファイルが存在しないか、壊れている可能性があります。");
            }

            pg.grabPixels();
            data = (int[])pg.getPixels();

            int gray;
            int temp = 0;

            double t1;
            double t2;

            for (int i = 0; (i < h) && (isActive); i += ACCURACY + 1) {
                for (int j = 0; (j < w) && (isActive); j += ACCURACY) {
                    Color color = new Color(data[i * w + j]);
                    gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                    jta.append("" + stringToUse.charAt(gray * stringToUse.length() / 256));
                    t1 = (double)(i * w + j);
                    t2 = (double)(w * h);

                    temp = (int)((t1 / t2) * 100);
                    setTitle("AA(" + (int)((t1 / t2) * 100) + "% - 生成中)");
                }
                jta.append("\n");
            }
            if (isActive) setTitle("AA(100% - 完了)");
            else setTitle("AA(" + temp + "% - キャンセルされました)");
            ctrl.setBtnsEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        jta.setText("");
        setTitle("AA(0% - 待機中)");
        isActive = true;
    }

    public void setFONT_SIZE(int FONT_SIZE) {
        this.FONT_SIZE = FONT_SIZE;
    }

    public void setACCURACY(int ACCURACY) {
        this.ACCURACY = ACCURACY;
    }

    public void setStringToUse(String stringToUse) {
        this.stringToUse = stringToUse;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setJta(JTextArea jta) {
        this.jta = jta;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
}
