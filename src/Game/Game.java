package Game;

import Entity.Entity;
import Entity.Player;
import Input.KeyInput;
import Input.MouseInput;
import Tile.Wall;
import gfx.Sprite;
import gfx.SpriteSheet;
import gfx.gul.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 320;
    public static final int HEIGHT = 180;
    public static final int SCALE = 4;
    public static final String TITLE ="Pesho";

    private Thread thread;
    private boolean runing = false;

    private static BufferedImage[] levels;
    public static int cods =0;
    public static int level =0;
    public static int countKoz =0;
    public static String finalPrint ;

    public static boolean playing = false;

    public static Handler handler;
    public static SpriteSheet sheet;
    public static Camera cam;
    public static Launcher launcher;
    public static MouseInput mouse;

    public static Sprite grass;
    public static Sprite floor;
    public static Sprite cod;
    public static Sprite blok;
    public static Sprite smoking;
    public static Sprite keyB;

    public static Sprite[] player;
    public static Sprite[] bugs;
    public static Sprite[] koz;
    public static Sprite[] finalLevel;
    public static Sprite[] cannabis;
    public static String[] elementsLevel1 , elementsLevel2,elementsLevel3,elementsLevel4;
    public  Game(){
        Dimension size = new Dimension(WIDTH*SCALE,HEIGHT*SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
    }
    private void init(){
        handler = new Handler();
        sheet = new SpriteSheet("/Sprite.png");
        cam = new Camera();
        launcher = new Launcher();
        mouse = new MouseInput();
        addKeyListener(new KeyInput());
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        elementsLevel1 = new String[]{"","public","class","HelloJava","public","static","void","main(String[] args)","System.out.println","(\"Hello Java!\")"};
        elementsLevel2 = new String[]{"","q","w","e","r","t","y","u","i","o"}; // element lvl2
        elementsLevel3 = new String[]{""}; // element lvl3
        elementsLevel4 = new String[]{""}; // element lvl4
        player = new Sprite[6];
        bugs = new Sprite[6];
        koz = new Sprite[6];
        finalLevel = new Sprite[10];
        cannabis = new Sprite[6];
        levels = new BufferedImage[4];
        cod = new Sprite(7,2,sheet);
        blok = new Sprite(7,3,sheet);
        smoking = new Sprite(1,7,sheet);
        keyB = new Sprite(5,7,sheet);
        if (level == 0){
            floor = new Sprite(2,1,sheet);
            grass = new Sprite(1,1,sheet);
            finalPrint="Hello Java";
        }
        for (int i = 0; i <player.length ; i++) {
            player[i] = new Sprite(i+1,2,sheet);
        }
        for (int i = 0; i <bugs.length ; i++) {
            bugs[i] = new Sprite(i+1,3,sheet);
        }
        for (int i = 0; i <koz.length ; i++) {
            koz[i] = new Sprite(i+1,6,sheet);
        }
        for (int i = 0; i <finalLevel.length ; i++) {
            finalLevel[i] = new Sprite(i+1,4,sheet);
        }
        for (int i = 0; i <cannabis.length ; i++) {
            cannabis[i] = new Sprite(i+1,8,sheet);
        }
        try {
            levels[0] = ImageIO.read(getClass().getResource("/level1.png"));
            levels[1] = ImageIO.read(getClass().getResource("/level2.png"));
            levels[2] = ImageIO.read(getClass().getResource("/level2.png"));
            levels[3] = ImageIO.read(getClass().getResource("/level2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler.clearLevel();
        handler.createLevel(levels[level]);
    }
    private synchronized void start(){
        if (runing) return;
        runing = true;
        thread = new Thread(this,"Thread");
        thread.start();
    }
    private synchronized void stop(){
        if (!runing)return;
        runing = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void run(){
        init();
        requestFocus();
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta =0.0;
        double ns = 1000000000.0/60.0;
        int frames =0;
        int ticks =0;
        while (runing){
            long now = System.nanoTime();
            delta += (now-lastTime)/ns;
            lastTime = now;
            while (delta >= 1){
                tick();
                ticks++;
                delta--;
            }
            render();
            frames++;
            if (System.currentTimeMillis()-timer >100){
                timer += 1000;
                //System.out.println(frames + " Frame Per Second " + ticks + " Updates Per Second");
                frames =0;
                ticks =0;

            }
        }
        stop();
    }
    public void  render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null){
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.cyan);
        g.fillRect(0,0,getWidth(),getHeight());
        g.drawImage(Game.cod.getBufferImage(),20,20,75,75,null);
        if (countKoz!=0){
            g.drawImage(Game.keyB.getBufferImage(),100,30,75,75,null);
        }
        g.setColor(Color.white);
        g.setFont(new Font("Courier", Font.BOLD, 20));

        if (level ==0) {
            for (int i = 0; i < elementsLevel1.length; i++) {

                    if (cods >=i) g.drawString(elementsLevel1[i], 30, 90+i*20);
                    g.drawString(" " + cods + " / " + (elementsLevel1.length - 1), 30, 85);
            }
        }
        if (level ==1) {
            for (int i = 0; i < elementsLevel2.length; i++) {

                if (cods >=i) g.drawString(elementsLevel2[i], 30, 90+i*20);
                g.drawString(" " + cods + " / " + (elementsLevel2.length - 1), 30, 85);
            }
        }
        if (level ==2) {
            //Print element
        }
        if (level ==3) {
            //Print element
        }

        if(playing)g.translate(cam.getX(),cam.getY());
        if(playing) handler.render(g);
        else if (!playing) launcher.render(g);
        g.dispose();
        bs.show();
    }
    public void tick(){
      if (playing)handler.tick();

        for (Entity e : handler.entity){
            if (e.getId()==Id.player){
                cam.tick(e);
            }
        }

    }

    public static int getFrameWight(){
        return WIDTH*SCALE;
    }
    public static int getFrameHeight(){
        return HEIGHT*SCALE;
    }
    public static void switchLevel(){
        cods =0;
        handler.clearLevel();
        handler.createLevel(levels[level]);
        if (level == 1){
            floor = new Sprite(4,1,sheet);
            grass = new Sprite(3,1,sheet);
            finalPrint="level2";

        }if (level == 2){
            floor = new Sprite(6,1,sheet);
            grass = new Sprite(5,1,sheet);
        }if (level == 3){
            floor = new Sprite(2,1,sheet);
            grass = new Sprite(7,1,sheet);
        }
    }
    public static Rectangle getVisibleArea(){
        for (int i = 0; i < handler.entity.size(); i++) {
            Entity e = handler.entity.get(i);
            if (e.getId()==Id.player)return new Rectangle(e.getX()-(getFrameWight()/2-5),e.getY()-(getFrameHeight()/2-5),getFrameWight()+10,getFrameHeight()+10);
        }
        return  null;
    }

    public  static void main(String[] args){
        Game game = new Game();
        JFrame frame = new JFrame(TITLE);
        frame.add(game);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.start();

    }


}
