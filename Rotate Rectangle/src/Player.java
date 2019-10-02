import java.awt.*;

public class Player extends GameObject {

    Handler handler;

    private int width = 30;
    private int height = 15;

    private double theta = Math.PI / 6;

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

    public Player(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        x_coords[0] = (int) x - (width / 2);
        x_coords[1] = (int) x - (width / 2);
        x_coords[2] = (int) x + (width / 2);
        x_coords[3] = (int) x + (width / 2);

        y_coords[0] = (int) y - (height / 2);
        y_coords[1] = (int) y + (height / 2);
        y_coords[2] = (int) y + (height / 2);
        y_coords[3] = (int) y - (height / 2);

        for(int i = 0; i < 4; i++) {
            adjustedXCoords[i] = (int) (((x_coords[i] - x) * Math.cos(theta)) + (y_coords[i] - y) * Math.sin(theta) + x);
            adjustedYCoords[i] = (int) ((-(x_coords[i] - x) * Math.sin(theta)) + (y_coords[i] - y) * Math.cos(theta) + y);
        }

        System.out.println("x: " + x);
        System.out.println("y: " + y);


    }

    @Override
    public void render(Graphics g) {
//        g.fillRect((int) x, (int) y, width, height);
        Polygon p = new Polygon(x_coords, y_coords, 4);
        g.setColor(Color.BLUE);
        g.fillPolygon(p);
        p = new Polygon(adjustedXCoords, adjustedYCoords, 4);
        g.fillPolygon(p);
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }


}
