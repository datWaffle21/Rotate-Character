package core.player;

import core.main.Game;
import core.main.GameObject;
import core.main.ID;

import javax.swing.*;
import java.awt.*;

public class Player extends GameObject {

    private int mx, my;
    private double theta;
    Polygon p;

    Point cursor;
    Game game;

    private int width = 40;
    private int height = 10;

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


    private float health = 100f;


    public Player(float x, float y, ID id, Game game) {
        super(x, y, id);
        this.game = game;

        p = new Polygon(x_coords, y_coords, 4);


        cursor = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(cursor, this.game);
        mx = cursor.x;
        my = cursor.y;

        theta = 0;

        System.out.println("X: " + mx);
        System.out.println("Y: " + my);
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




        cursor = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(cursor, game);
        mx = cursor.x;
        my = cursor.y;
        double diffX = mx - x;
        double diffY = my - y;

        theta = Math.pow(Math.atan(diffX / diffY), 1);

        for(int i = 0; i < 4; i++) {
            adjustedXCoords[i] = (int) (((x_coords[i] - x) * Math.cos(theta)) + (y_coords[i] - y) * Math.sin(theta) + x);
            adjustedYCoords[i] = (int) ((-(x_coords[i] - x) * Math.sin(theta)) + (y_coords[i] - y) * Math.cos(theta) + y);
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
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 40, 10);
    }
}
