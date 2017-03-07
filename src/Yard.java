import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by 桢 on 2017/2/21.
 */
public class Yard extends Frame {
    public static final int ROWS = 24;
    public static final int COLS = 24;
    public static final int WIDTH = 30;
    Image offScreenImage;
    public static int sleeptime = 200;

    Snake snake = new Snake();
    Egg egg = new Egg();
    boolean flag = snake.isAlive();
    PaintThreat paintThreat = new PaintThreat();


    public static void main(String[] args) {
        Yard yard = new Yard();
        yard.launch();
    }

    public void launch() {
        setBounds(100, 100, COLS * WIDTH, ROWS * WIDTH);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        addKeyListener(new KeyMonitor());
        new Thread(paintThreat).start();

    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, COLS * WIDTH, ROWS * WIDTH);
        g.setColor(Color.BLACK);
        for (int i = 0; i < ROWS; i++) {
            g.drawLine(0, WIDTH * i, COLS * WIDTH, WIDTH * i);
        }
        for (int i = 0; i < COLS; i++) {
            g.drawLine(WIDTH * i, 0, WIDTH * i, WIDTH * ROWS);
        }
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Times New Roman", Font.BOLD, 15));
        g.drawString("Press UP to speed up, DOWN to slow down, F2 to replay after GAMEOVER", 140, 70);
        g.drawString("Press P to pause the game, C to continue", 140, 90);
        g.setFont(new Font("Times New Roman", Font.BOLD, 20));
        g.setColor(Color.CYAN);
        int speedscore = (200 - sleeptime) / 40;
        g.drawString("Speed: " + (speedscore + 1), 15, 70);
        g.setFont(new Font("Times New Roman", Font.BOLD, 20));
        g.setColor(Color.blue);
        int score = snake.score;
        System.out.println("Score: " + score + "  Speed: " + speedscore);
        score = score + score * speedscore;
        g.drawString("Score: " + score, 15, 100);
        if (!snake.isAlive()) {
            g.setFont(new Font("Times New Roman", Font.BOLD, 60));
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 180, 350);
            paintThreat.gameOver();
        }
        g.setColor(c);
        snake.draw(g);
        egg.draw(g);
    }

    @Override
    public void update(Graphics g) {

        if (offScreenImage == null) {
            offScreenImage = this.createImage(COLS * WIDTH, ROWS * WIDTH);
        }
        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    public class KeyMonitor extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            snake.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_F2) {
                Yard yard = new Yard();
                snake = new Snake();
                egg = new Egg();
                yard.launch();
            }
            if (key == KeyEvent.VK_UP) {
                sleeptime -= 40;
            }
            if (key == KeyEvent.VK_DOWN) {
                sleeptime += 40;
            }
            if (key == KeyEvent.VK_P) {
                flag = false;
            }
            if (key == KeyEvent.VK_C) {
                flag = true;
                new Thread(paintThreat).start();
            }
        }
    }

    public class PaintThreat implements Runnable {
        @Override
        public void run() {

            while (flag) {
                repaint();
                try {
                    Thread.sleep(sleeptime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void gameOver() {
            flag = false;
        }
    }
}
