package Game;

import Entity.Entity;
import Input.KeyInput;
import Input.MouseInput;
import gfx.ImageLoad;
import gfx.Sprite;
import gfx.SpriteSheet;
import gfx.gul.Launcher;

import javax.annotation.Resources;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
    public static boolean phone = false;
    public static boolean drinkBeer = false;

    public static Handler handler;
    public static SpriteSheet sheet,barSheet,win;
    public static Camera cam;
    public static Launcher launcher;
    public static MouseInput mouse;

    public static Sprite grass,floor,barWall,cod,coin,blok, smoking,keyR;

    public static Sprite[] player;
    public static Sprite[] bugs;
    public static Sprite[] koz;
    public static Sprite[] finalLevel;
    public static Sprite[] bar;
    public static Sprite[] beer;
    public static Sprite[] cannabis;
    public static String[] elementsLevel1 , elementsLevel2,elementsLevel3,elementsLevel4;
    private static Window frame;

    public  Game(){
        Dimension size = new Dimension(WIDTH*SCALE,HEIGHT*SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);

    }

    public static Window getFrame() {
        return frame;
    }

    private void init(){
        handler = new Handler();
        sheet = new SpriteSheet("/Sprite.png");
        barSheet = new SpriteSheet("/bar.png");
        win = new SpriteSheet("/win.png");
        cam = new Camera();
        launcher = new Launcher();
        mouse = new MouseInput();


        addKeyListener(new KeyInput());
        addMouseListener(mouse);
        addMouseMotionListener(mouse);

        elementsLevel1 = new String[]{"","public","class","HelloWorld","public","static","void","main(String[] args)","System.out.println","(\"Hello World!\")"};
        elementsLevel2 = new String[]{"","public","class","BasicLoop","public","static","void","main(String[] args)","for","(int i = 1; i <= 10; i++)","System.out.printf(\"%-2d\",i);", "\"1 2 3 4 5 6 7 8 9 10\""}; // element lvl2
        elementsLevel3 = new String[]{""}; // element lvl3
        elementsLevel4 = new String[]{"","public class","AlienGreeting","public static void","main(String[] args)","byte[] encryptedMsg =", "{97,121,121,32,108,109,97,111};","System.out.print(\"Translated:\"", "+ new String(encryptedMsg));"};

        player = new Sprite[6];
        bugs = new Sprite[6];
        koz = new Sprite[6];
        finalLevel = new Sprite[10];
        bar = new Sprite[9];
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
            finalPrint="Hello World!";
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
                render();
                tick();
                delta--;
            }

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
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(Game.cod.getBufferImage(),20,20,75,75,null);
        g.drawImage(Game.coin.getBufferImage(),100,20,75,75,null);
        g.setColor(Color.white);
        g.setFont(new Font("Courier", Font.BOLD, 20));
        g.drawString(""+coins,170,90);
        if (countKoz!=0){
            g.drawImage(Game.keyR.getBufferImage(),200,20,75,75,null);
        }


        if (level ==0) {
            if (phone && !drinkBeer){
                g.drawImage(ImageLoad.loadImage("/phone.png"),320,0,null);
            } else if (phone){
                g.drawImage(ImageLoad.loadImage("/phoneDrinkBeer.png"),320,0,null);
            }
            elemetn = elementsLevel1.length;
            for (int i = 0; i < elemetn; i++) {

                if (countCode >=i) g.drawString(elementsLevel1[i], 30, 90+i*20);
                g.drawString(" " + countCode + " / " + (elemetn - 1), 30, 85);

            }
        }
        if (level ==1) {
            if (phone && !drinkBeer){
                g.drawImage(ImageLoad.loadImage("/phoneLvl2.png"),320,0,null);
            } else if (phone){
                g.drawImage(ImageLoad.loadImage("/phoneDrinkBeer.png"),320,0,null);
            }
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
        } if (level == 4){
            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(), getHeight());
           g.drawImage(ImageLoad.loadImage("/win.png"),0,0,null);
        }


        if(playing)g.translate(cam.getX(),cam.getY());
        if(playing) {
            handler.render(g);

        }
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
        drinkBeer = false;
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

    public static void main(String[] args) throws MalformedURLException {
        Game game = new Game();
        JFrame frame = new JFrame(TITLE);
        ImageIcon icon = new ImageIcon("D:\\Software Univerity Sofia softuni.bg\\C# Fundamental level\\Java\\TeamWorkProject\\PeshoGame\\trunk\\res\\icon.png");
        frame.setIconImage(icon.getImage());
        frame.add(game);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        game.start();


    }


}
