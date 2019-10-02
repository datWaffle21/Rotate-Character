import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable{

    Thread thread;
    Handler handler;
    Player player;
    KeyInput keyInput;

    private boolean running = false;

    private int showFrames;

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

        g.setColor(Color.GREEN);
        g.setFont(new Font("arial", Font.BOLD, 10));
        g.drawString(showFrames + "", 7, 15);

        handler.render(g);

        g.dispose();
        bs.show();
    }

    public void tick() {
        handler.tick();
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                 tick();
                delta--;

            }

            if (running) {
                render();
                frames++;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                showFrames = frames;
                frames = 0;
            }
        }
        stop();
    }

    public Main() {
        handler = new Handler();
        player = new Player((Constants.WIDTH / 2.0f), (Constants.HEIGHT / 2.0f), ID.Player, handler);
//        hud = new HUD(this, handler);
//        spawner = new Spawn(handler, hud, this);
//        menu = new Menu(this, handler, hud, spawner);
        keyInput = new KeyInput(handler,this);
        handler.addObject(player);

        this.addKeyListener(keyInput);
//        this.addMouseListener(menu);

        new Window(Constants.WIDTH, Constants.HEIGHT, "Wave -- Remastered", this);
    }

    synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    private synchronized void stop() {
        try {
            thread.join();
            running = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }

}
