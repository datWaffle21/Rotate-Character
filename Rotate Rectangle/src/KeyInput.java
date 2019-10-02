import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private Main main;
    private Handler handler;
    private boolean[] keyDown = new boolean[4];

    public KeyInput(Handler handler, Main main) {
        this.handler = handler;
        this.main = main;

        keyDown[0] = false;
        keyDown[1] = false;
        keyDown[2] = false;
        keyDown[3] = false;
    }

    public void tick() {

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.getObject().size(); i++) {

            GameObject tempObject = handler.getObject().get(i);

            if (tempObject.getId() == ID.Player) {
                // Key events for the core.player
                if (key == KeyEvent.VK_W) {
                    tempObject.setVelY(-3);
                    keyDown[0] = true;
                }
                if (key == KeyEvent.VK_S) {
                    tempObject.setVelY(+3);
                    keyDown[1] = true;
                }
                if (key == KeyEvent.VK_A) {
                    tempObject.setVelX(-3);
                    keyDown[2] = true;
                }
                if (key == KeyEvent.VK_D) {
                    tempObject.setVelX(+3);
                    keyDown[3] = true;
                }
            }


            //Add more if-statements like above for more characters
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for (int i = 0; i < handler.getObject().size(); i++) {
            GameObject tempObject = handler.getObject().get(i);

            if (tempObject.getId() == ID.Player) {
                // Key events for the core.player
                if (key == KeyEvent.VK_W) keyDown[0] = false; //tempObject.setVelY(0);
                if (key == KeyEvent.VK_S) keyDown[1] = false; //tempObject.setVelY(0);
                if (key == KeyEvent.VK_A) keyDown[2] = false; //tempObject.setVelX(0);
                if (key == KeyEvent.VK_D) keyDown[3] = false; //tempObject.setVelX(0);

                // Vertical movement
                if (!keyDown[0] && !keyDown[1]) tempObject.setVelY(0);
                // Horizontal movement
                if (!keyDown[2] && !keyDown[3]) tempObject.setVelX(0);
            }


            //Add more if-statements like above for more characters
        }


    }

}