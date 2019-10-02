import java.awt.*;
import java.util.LinkedList;

public class Handler {

    private LinkedList<GameObject> object = new LinkedList<>();


    public LinkedList<GameObject> getObject() {
        return object;
    }

    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            tempObject.tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            tempObject.render(g);
        }
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);

    }

    public void removePlayer() {
        for (int i = 0; i < this.object.size(); i++) {
            GameObject tempObject = this.object.get(i);
            if (tempObject.getId() == ID.Player) {
                this.removeObject(tempObject);
                i--;
            }
        }
    }

    public void removeAll() {
        for (int i = 0; i < this.object.size(); i++) {
            GameObject tempObject = this.object.get(i);
            if (!(tempObject.getId() == (ID.Player))) {
                this.removeObject(tempObject);
                i--;
            }
        }
    }
}