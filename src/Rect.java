import java.awt.*;

public class Rect {

    private Point point;

    public Point getPoint() {
        return point;
    }

    public Rect(int x, int y) {

        point = new Point(x, y);

    }

    public void moveUpAndDown(String direction) {
        //Method to move the player up or down by changing point y.

        if (direction.equals("up"))
            point.y -= 5;
        else
            point.y += 5;

    }


}
