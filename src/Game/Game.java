package Game;

import Entity.Entity;
import Input.KeyInput;
import Input.MouseInput;
import gfx.Sprite;
import gfx.SpriteSheet;
import gfx.gul.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 320;
    public static final int HEIGHT = 180;
    public static final int SCALE = 4;
    public static final String TITLE ="Super Pesho";

    private Thread thread;
    private boolean runing = false;

    private static BufferedImage[] levels;

    public static int countCode =0;
    public static int coins =0;
    public static int level =0;
    public static int countKoz =0;
    public static int elemetn;

    public static String finalPrint ;

    public static boolean playing = false;

    public static Handler handler;
    public static SpriteSheet sheet,barSheet;
    public static Camera cam;
    public static Launcher launcher;
    public static MouseInput mouse;

    public static Sprite grass;
    public static Sprite floor;
    public static Sprite barWall;
    public static Sprite cod;
    public static Sprite coin;
    public static Sprite blok;
    public static Sprite smoking;
    public static Sprite keyR;

    public static Sprite[] player;
    public static Sprite[] bugs;
    public static Sprite[] koz;
    public static Sprite[] finalLevel;
    public static Sprite[] bar;
    public static Sprite[] beer;
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
        barSheet = new SpriteSheet("/bar.png");
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
        bar = new Sprite[10];
        cannabis = new Sprite[6];
        beer = new Sprite[2];
        levels = new BufferedImage[5];

        barWall = new Sprite(1,2,barSheet);
        cod = new Sprite(7,2,sheet);
        coin = new Sprite(8,2,sheet);
        blok = new Sprite(7,3,sheet);
        smoking = new Sprite(1,7,sheet);
        keyR = new Sprite(5,7,sheet);

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

        for (int i = 0; i <bar.length ; i++) {
            bar[i] = new Sprite(i+1,1,barSheet);
        }

        for (int i = 0; i <beer.length ; i++) {
            beer[i] = new Sprite(i+1,3,barSheet);
        }

        for (int i = 0; i <cannabis.length ; i++) {
            cannabis[i] = new Sprite(i+1,8,sheet);
        }
        try {
            levels[0] = ImageIO.read(getClass().getResource("/level1.png"));
            levels[1] = ImageIO.read(getClass().getResource("/level2.png"));
            levels[2] = ImageIO.read(getClass().getResource("/level3.png"));
            levels[3] = ImageIO.read(getClass().getResource("/level4.png"));
            levels[4] = ImageIO.read(getClass().getResource("/win.png"));
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
        double fps = 60.0;
        double delta =0.0;
        double ns = 1000000000.0/ fps;

        while (runing){
            long now = System.nanoTime();
            delta += (now-lastTime)/ns;
            lastTime = now;
            while (delta >= 1){
                tick();
                delta--;
            }
            render();
        }
        stop();
    }
    public void  render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null){
            createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.cyan);
        g.fillRect(0,0,getWidth(),getHeight());
        g.drawImage(Game.cod.getBufferImage(),20,20,75,75,null);
        g.drawImage(Game.coin.getBufferImage(),100,20,75,75,null);
        g.setColor(Color.white);
        g.setFont(new Font("Courier", Font.BOLD, 20));
        g.drawString(""+coins,170,90);
        if (countKoz!=0){
            g.drawImage(Game.keyR.getBufferImage(),200,20,75,75,null);
        }


        if (level ==0) {
            elemetn = elementsLevel1.length;
            for (int i = 0; i < elemetn; i++) {

                    if (countCode >=i) g.drawString(elementsLevel1[i], 30, 90+i*20);
                    g.drawString(" " + countCode + " / " + (elemetn - 1), 30, 85);
            }
        }
        if (level ==1) {
            elemetn = elementsLevel2.length;
            for (int i = 0; i < elemetn; i++) {

                if (countCode >=i) g.drawString(elementsLevel2[i], 30, 90+i*20);
                g.drawString(" " + countCode + " / " + (elemetn - 1), 30, 85);
            }
        }
        if (level ==2) {
            elemetn = elementsLevel3.length;
            for (int i = 0; i < elemetn; i++) {
                if (countCode >=i) g.drawString(elementsLevel3[i], 30, 90+i*20);
                g.drawString(" " + countCode + " / " + (elemetn - 1), 30, 85);
            }
        }
        if (level ==3) {
            elemetn = elementsLevel4.length;
            for (int i = 0; i < elemetn; i++) {
                if (countCode >=i) g.drawString(elementsLevel4[i], 30, 90+i*20);
                g.drawString(" " + countCode + " / " + (elemetn - 1), 30, 85);
            }
        }

        if(playing)g.translate(cam.getX(),cam.getY());
        if(playing) handler.render(g);
        else if (!playing) launcher.render(g);
        g.dispose();
        bs.show();
    }
    public void tick(){
      if (playing)handler.tick();
        for (int j = 0; j < handler.entity.size(); j++) {
            Entity e = handler.entity.get(j);
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
        countCode =0;
        handler.clearLevel();
        handler.createLevel(levels[level]);
        if (level == 1){
            floor = new Sprite(4,1,sheet);
            grass = new Sprite(3,1,sheet);
            finalPrint="level2";

        }if (level == 2){
            floor = new Sprite(6,1,sheet);
            grass = new Sprite(5,1,sheet);
            finalPrint="level3";
        }if (level == 3){
            floor = new Sprite(2,1,sheet);
            grass = new Sprite(7,1,sheet);
            finalPrint="level4";
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
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.start();

    }


}
