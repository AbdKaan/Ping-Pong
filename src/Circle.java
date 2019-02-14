import java.awt.*;

public class Circle {

    private int r;
    private Point point;

    public Point getPoint() {
        return point;
    }

    public int getR() {
        return r;
    }

    public Circle(int x, int y) {

        r = 30;
        point = new Point(x, y);

    }

    public void move(int speedX, int speedY) {
        //Method to move the ball accordingly to the speed in the PingPong class.

        point.x += speedX;
        point.y += speedY;

    }
}
