import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {

    private final int WEIGHT = 640;
    private final int HEIGHT = 608;
    private final int DOT_SIZE = 32;
    private final int ALL_DOTS = 400;
    private final int FREQUENCY = 250;
    private Image dot;
    private Image apple;
    private Snake snake;
    private int appleX;
    private int appleY;
    private Timer timer;
    private boolean inGame = true;

    public GameField() {
        setBackground(Color.DARK_GRAY);
        loadImages();
        initGame();
        addKeyListener(new KeyFieldListener());
        setFocusable(true);
    }

    public void initGame() {
        snake = new Snake(ALL_DOTS);
        for (int i = 0; i < snake.getSize(); i++) {
            snake.setXi(i, 64);
            snake.setYi(i, 64 + i*DOT_SIZE);
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
            for (int i = 0; i < snake.getSize(); i++) {
                g.drawImage(dot, snake.getXi(i), snake.getYi(i), this);
            }
        }
        else {
            String s = "Game Over";
            g.setColor(Color.WHITE);
            g.setFont(new Font("Harrington", Font.BOLD, DOT_SIZE*2));
            g.drawString(s, WEIGHT /4, HEIGHT /2);
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
        for (int i = snake.getSize(); i > 0; i--) {
            snake.setXi(i, snake.getXi(i-1));
            snake.setYi(i, snake.getYi(i-1));

        }
        switch (snake.getDirection()) {
            case UP:
                snake.changeYi(0, -DOT_SIZE);
                break;
            case DOWN:
                snake.changeYi(0, DOT_SIZE);
                break;
            case LEFT:
                snake.changeXi(0, -DOT_SIZE);
                break;
            case RIGHT:
                snake.changeXi(0, DOT_SIZE);
        }
    }

    public void checkApple() {
        if (snake.getXi(0)==appleX && snake.getYi(0)==appleY) {
            snake.incSize();
            createApple();
        }
    }

    public void checkBorders() {
        for (int i = snake.getSize(); i > 0; i--) {
            if (i>4 && snake.getXi(0) == snake.getXi(i) && snake.getYi(0) == snake.getYi(i))
                inGame = false;
        }
        if (snake.getXi(0) < 0 || snake.getXi(0) > WEIGHT || snake.getYi(0) < 0 || snake.getYi(0) > HEIGHT)
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
            Direction d = snake.getDirection();
            if (key == KeyEvent.VK_LEFT && d!=Direction.RIGHT ) {
                snake.setDirection(Direction.LEFT);
            }
            else if (key == KeyEvent.VK_RIGHT && d != Direction.LEFT) {
                snake.setDirection(Direction.RIGHT);
            }
            else if (key == KeyEvent.VK_UP && d != Direction.DOWN) {
                snake.setDirection(Direction.UP);
            }
            else if (key == KeyEvent.VK_DOWN && d != Direction.UP) {
                snake.setDirection(Direction.DOWN);
            }
        }
    }
}
