import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {

    private final int SIZE = 640;
    private final int DOT_SIZE = 32;
    private final int ALL_DOTS = 400;
    private final int FREQUENCY = 250;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int sizeSnake;
    private Timer timer;
    private boolean left, right, up, down = true, inGame = true;

    public GameField() {
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new KeyFieldListener());
        setFocusable(true);
    }

    public void initGame() {
        sizeSnake = 3;
        for (int i = 0; i < sizeSnake; i++) {
            x[i] = 64;
            y[i] = 64 + i*DOT_SIZE;
        }
        timer = new Timer(FREQUENCY, this);
        timer.start();
        createApple();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < sizeSnake; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        }
        else {
            String s = "Game Over";
            g.setColor(Color.WHITE);
            g.setFont(new Font("Harrington", Font.BOLD, DOT_SIZE*2));
            g.drawString(s, SIZE/4, SIZE/2);
        }
    }

    public void createApple() {
        appleX = new Random().nextInt(20)*DOT_SIZE;
        appleY = new Random().nextInt(20)*DOT_SIZE;
    }

    private void loadImages() {
        ImageIcon appleIcon = new ImageIcon("apple3.png");
        apple = appleIcon.getImage();
        ImageIcon dotIcon = new ImageIcon("square.png");
        dot = dotIcon.getImage();
    }

    public void move() {
        for (int i = sizeSnake; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0]==appleY) {
            sizeSnake++;
            createApple();
        }
    }

    public void checkBorders() {
        for (int i = sizeSnake; i > 0; i--) {
            if (i>4 && x[0] == x[i] && y[0] == y[i])
                inGame = false;
        }
        if (x[0] < 0 || x[0] > SIZE || y[0] < 0 || y[0] > SIZE)
            inGame = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkBorders();
            move();
        }
        repaint();
    }

    private class KeyFieldListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            else if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            else if (key == KeyEvent.VK_UP && !down) {
                up = true;
                left = false;
                right = false;
            }
            else if (key == KeyEvent.VK_DOWN && !up) {
                down = true;
                left = false;
                right = false;
            }
        }
    }
}
