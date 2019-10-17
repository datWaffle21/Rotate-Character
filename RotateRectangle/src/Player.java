import javax.swing.*;
import java.awt.*;

public class Player extends GameObject {

    private int mx, my;
    private double theta;
    Polygon p;

    Point cursor;
    Main main;

    double diffX = 0;
    double diffY = 0;

    private int width = 40;
    private int height = 10;

    private double viewAngleMax = (85 / 2) * (Math.PI / 180);
    private double viewDistance = 150;

    private int[] x_coords = {
            (int) x - (width / 2),
            (int) x - (width / 2),
            (int) x + (width / 2),
            (int) x + (width / 2)
    };

    private int[] y_coords = {
            (int) y - (height / 2),
            (int) y + (height / 2),
            (int) y + (height / 2),
            (int) y - (height / 2)
    };

    private int[] adjustedXCoords = {
            (int) x - (width / 2),
            (int) x - (width / 2),
            (int) x + (width / 2),
            (int) x + (width / 2)
    };

    private int[] adjustedYCoords = {
            (int) y - (height / 2),
            (int) y + (height / 2),
            (int) y + (height / 2),
            (int) y - (height / 2)
    };

    public Player(float x, float y, ID id, Main main) {
        super(x, y, id);
        this.main = main;

        p = new Polygon(x_coords, y_coords, 4);

        cursor = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(cursor, this.main);
        mx = cursor.x;
        my = cursor.y;

        diffX = mx - x;
        diffY = my - y;

        theta = 0;

        System.out.println("X: " + mx);
        System.out.println("Y: " + my);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        cursor = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(cursor, main);
        mx = cursor.x;
        my = cursor.y;

        x_coords[0] = (int) x - (width / 2);
        x_coords[1] = (int) x - (width / 2);
        x_coords[2] = (int) x + (width / 2);
        x_coords[3] = (int) x + (width / 2);

        y_coords[0] = (int) y - (height / 2);
        y_coords[1] = (int) y + (height / 2);
        y_coords[2] = (int) y + (height / 2);
        y_coords[3] = (int) y - (height / 2);

        diffX = mx - x;
        diffY = my - y;

        theta = Math.atan(diffX / diffY);

        for (int i = 0; i < 4; i++) {
            adjustedXCoords[i] = (int) (((x_coords[i] - x) * Math.cos(theta)) + (y_coords[i] - y) * Math.sin(theta) + x);
            adjustedYCoords[i] = (int) ((-(x_coords[i] - x) * Math.sin(theta)) + (y_coords[i] - y) * Math.cos(theta) + y);
        }
    }

    //Draws a line to the mouse starting from the character and ending at the mouse if the mouse is within the viewdist and stops when it reaches the viewdist
    public void drawLineWithFOV(Graphics g) {
        double magnitude = Math.sqrt((diffX * diffX) + (diffY * diffY));
        double newLineX = diffX / magnitude;
        double newLineY = diffY / magnitude;
        newLineX *= viewDistance;
        newLineY *= viewDistance;
        if (magnitude < viewDistance) {
            g.drawLine((int) x, (int) y, (int) (mx), (int) (my));
        } else {
            g.drawLine((int) x, (int) y, (int) (newLineX + x), (int) (newLineY + y));
        }
        drawFOV(g, magnitude);
    }

    public void drawFOV(Graphics g, double magnitude) {
        double fovx1;
        double fovx2;

        double fovy1;
        double fovy2;

        fovx1 = (magnitude * Math.sin(theta + viewAngleMax));
        fovx2 = (magnitude * Math.sin(theta - viewAngleMax));

        fovy1 = (magnitude * Math.cos(theta + viewAngleMax));
        fovy2 = (magnitude * Math.cos(theta - viewAngleMax));

        double fovmag = Math.sqrt((fovx1 * fovx1) + (fovy1 * fovy1)); //

        if (y < my) {
            if (fovmag <= viewDistance) {
                g.drawLine((int) x, (int) y, (int) (fovx1 + x), (int) (fovy1 + y));
                g.drawLine((int) x, (int) y, (int) (fovx2 + x), (int) (fovy2 + y));
            } else {
                g.drawLine((int) x, (int) y, (int) ((fovx1 / fovmag * viewDistance) + x), (int) ((fovy1 / fovmag * viewDistance) + y));
                g.drawLine((int) x, (int) y, (int) ((fovx2 / fovmag * viewDistance) + x), (int) ((fovy2 / fovmag * viewDistance) + y));
            }
        } else {
            double temp = theta;
            temp += (Math.PI);
            fovx1 = (magnitude * Math.sin(temp + viewAngleMax));
            fovx2 = (magnitude * Math.sin(temp - viewAngleMax));

            fovy1 = (magnitude * Math.cos(temp + viewAngleMax));
            fovy2 = (magnitude * Math.cos(temp - viewAngleMax));

            if (fovmag < viewDistance) {
                g.drawLine((int) x, (int) y, (int) (fovx1 + x), (int) (fovy1 + y));
                g.drawLine((int) x, (int) y, (int) (fovx2 + x), (int) (fovy2 + y));
            } else {
                g.drawLine((int) x, (int) y, (int) ((fovx1 / fovmag * viewDistance) + x), (int) ((fovy1 / fovmag * viewDistance) + y));
                g.drawLine((int) x, (int) y, (int) ((fovx2 / fovmag * viewDistance) + x), (int) ((fovy2 / fovmag * viewDistance) + y));
            }
        }
    }

    @Override
    public void render(Graphics g) {
//        g.fillRect((int) x, (int) y, width, height);
        Polygon p = new Polygon(x_coords, y_coords, 4);
        g.setColor(Color.BLUE);
//        g.fillPolygon(p);
        p = new Polygon(adjustedXCoords, adjustedYCoords, 4);
        g.fillPolygon(p);

        g.setColor(Color.WHITE);

        drawLineWithFOV(g);
        g.drawOval((int) (x - (viewDistance)), (int) (y - (viewDistance)), (int) viewDistance * 2, (int) viewDistance * 2);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 40, 10);
    }
}