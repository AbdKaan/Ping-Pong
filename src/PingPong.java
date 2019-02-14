//Abdullah Kaan 115200054
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class PingPong extends JFrame implements ActionListener{

    private JLabel lblScore;
    private JButton btnStart;
    private Rect player1;
    private Rect player2;
    private Circle ball;
    private Timer timer;
    private int speedX;
    private int speedY;
    private Random rng;
    private boolean upPressed;
    private boolean downPressed;
    private boolean sPressed;
    private boolean wPressed;
    private int player1Score;
    private int player2Score;
    private boolean gamePaused;

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        //Draw the shapes.
        g.setColor(Color.BLACK);
        g.fillRect(player1.getPoint().x, player1.getPoint().y, 20,70);
        g.fillRect(player2.getPoint().x, player2.getPoint().y, 20,70);

        g.setColor(Color.RED);
        g.fillOval(ball.getPoint().x, ball.getPoint().y, ball.getR(), ball.getR());

    }

    public void createDirection() {

        //Checking if the game was paused or not and the x location of the ball, then change the speed accordingly.

        int ballX = ball.getPoint().x;

        if (ballX < 390) {

            if (!gamePaused) {

                speedX = -speedX + 2;
                speedY = rng.nextInt(8) - 4;

            }

        } else if (ballX > 390) {

            if (!gamePaused) {

                speedX = -speedX - 2;
                speedY = rng.nextInt(8) - 4;

            }

        } else {

            if (!gamePaused) {

                speedX = rng.nextInt(8) - 4;
                speedY = rng.nextInt(8) - 4;
                if (speedX < 4 && speedX >= 0)
                    speedX += 4;
                else if (speedX > -4 && speedX <= 0)
                    speedX -= 4;

            }

        }

    }

    public PingPong() {

        setLayout(null);
        rng = new Random();
        upPressed = false;
        downPressed = false;
        sPressed = false;
        wPressed = false;
        player1Score = 0;
        player2Score = 0;

        btnStart = new JButton("Start");
        btnStart.setSize(70,50);
        btnStart.setBackground(Color.WHITE);
        btnStart.setLocation(365,30);

        btnStart.addKeyListener(keyListener);
        btnStart.addActionListener(actionEvent -> {
            /*Check if the button is "Start" or "Pause", set pause state(gamePaused) accordingly. Change the text
            accordingly to game state. If "Start" is clicked, createDirection() and start the timer so game starts else
            stop the timer so game pauses.*/

            if (actionEvent.getActionCommand().equals("Start")) {

                btnStart.setText("Pause");
                createDirection();
                gamePaused = false;
                timer.start();

            } else {

                timer.stop();
                gamePaused = true;
                btnStart.setText("Start");

            }
        });
        add(btnStart);

        player1 = new Rect(20, 260);//20 260
        player2 = new Rect(760, 260);//760 260
        ball = new Circle(390, 285);
        repaint();

        lblScore = new JLabel("Player1: " + player1Score + " Player2: " + player2Score);
        lblScore.setSize(150, 20);
        lblScore.setLocation(350, 500);
        add(lblScore);


        setSize(800,600);
        setTitle("PingPong");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        timer = new Timer(50, this);

    }

    private KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {

            int keyCode = keyEvent.getKeyCode();

            /*I'm using 4 different booleans for all the buttons so more than 1 button can be clicked at the same time
            and this way the lag you get from first holding the button is stopped too with the usage of keyReleased as
            well.*/

            if (keyCode == KeyEvent.VK_UP)

                upPressed = true;

             else if (keyCode == KeyEvent.VK_DOWN)

                downPressed = true;

             else if (keyCode == KeyEvent.VK_W)

                wPressed = true;

             else if (keyCode == KeyEvent.VK_S)

                sPressed = true;


        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {

            int keyCode = keyEvent.getKeyCode();

            //Keep going up or down until the button is released.

            if (keyCode == KeyEvent.VK_UP)

                upPressed = false;

            else if (keyCode == (KeyEvent.VK_DOWN))

                downPressed = false;

            else if (keyCode == (KeyEvent.VK_W))

                wPressed = false;

            else if (keyCode == (KeyEvent.VK_S))

                sPressed = false;

        }
    };

    public static void main(String[] args) {

        new PingPong();

    }

    public boolean collisionPlayer() {

        //Check if the ball collides with player1 or player2 by comparing their x and y values.

        if ((player1.getPoint().x + 20 - ball.getPoint().x >= 0 && player1.getPoint().y + 70 > ball.getPoint().y &&
                player1.getPoint().y - 35 < ball.getPoint().y)
                || (player2.getPoint().x - 30 - ball.getPoint().x <= 0 && player2.getPoint().y + 70 > ball.getPoint().y &&
                player2.getPoint().y - 35 < ball.getPoint().y)) {

            return true;

        }

        return false;

    }

    public boolean collisionLeftRight() {

        //Check if ball hit the left or right wall by checking the x value.

        if (ball.getPoint().x < 20 || ball.getPoint().x > 770) {

            return true;

        }

        return false;

    }

    public boolean collisionTopDown() {

        //Check if ball hit the top or bottom wall by checking the y value.

        if (ball.getPoint().y < 30 || ball.getPoint().y > 560) {

            return true;

        }

        return false;

    }

    public void restart() {

        //Stop the timer so the game pauses, check which player got the score, set players and the ball to default
        //location and speed. Change the score label.

        timer.stop();

        if (ball.getPoint().x > 390) {

            player1Score++;

        } else {

            player2Score++;

        }

        btnStart.setText("Start");

        player1.getPoint().setLocation(20, 260);

        player2.getPoint().setLocation(760, 260);

        ball.getPoint().setLocation(390,285);
        speedX = 0;
        speedY = 0;

        lblScore.setText("Player1: " + player1Score + " Player2: " + player2Score);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (collisionPlayer()) {
            //Change speed direction randomly while checking which player hit the ball with createDirection().

            createDirection();

        } else if (collisionTopDown()) {
            //Change y to negative of it so it goes the other way.

            speedY = -speedY;

        } else if (collisionLeftRight()) {
            //This means one player got a score so restart.

            restart();

        }


        //Check if the player is in the frame and move.
        if (wPressed && player1.getPoint().y > 35)

            player1.moveUpAndDown("up");

        if (sPressed && player1.getPoint().y < 525)

            player1.moveUpAndDown("down");

        if (upPressed && player2.getPoint().y > 35)

            player2.moveUpAndDown("up");

        if (downPressed && player2.getPoint().y < 525)

            player2.moveUpAndDown("down");

        ball.move(speedX,speedY);
        repaint();

    }

}
