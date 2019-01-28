import javax.swing.*;
import java.awt.*;

public class GameField extends JPanel {

    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int sizeSnake;
    private Timer timer;
    private boolean left, right, up = true, down, inGame = true;

    public GameField() {
        setBackground(Color.black);
        loadImages();
    }

    private void loadImages() {
        ImageIcon appleIcon = new ImageIcon("apple.svg");
        apple = appleIcon.getImage();
        ImageIcon dotIcon = new ImageIcon("blank-check-box.svg");
        dot = dotIcon.getImage();
    }
}
