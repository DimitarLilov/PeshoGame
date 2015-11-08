package Entity;

import Game.Game;
import Game.Handler;
import Game.Id;
import Tile.Tile;


import java.awt.*;
import java.util.Random;

public class Koz extends Entity {

    private int frame;
    private int frameDeloy;

    public Koz(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        Random random = new Random();
        int dir = random.nextInt(2);
        switch (dir){
            case 0:
                setVelX(-2);
                facing =0;
                break;
            case 1:
                setVelX(2);
                facing=1;
                break;
        }
    }

    @Override
    public void render(Graphics g) {

        if (facing == 0){
            g.drawImage(Game.koz[3+frame].getBufferImage(),x,y,width,height,null);
        } else if (facing ==1){
            g.drawImage(Game.koz[frame].getBufferImage(),x,y,width,height,null);
        }
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        for (int i = 0; i < handler.tile.size(); i++) {
            Tile t = handler.tile.get(i);
            if (t.isSolid()) {
                if (getBoundsBottom().intersects(t.getBounds())&&t.getId()!=Id.code) {
                    setVelY(0);
                    if (falling) falling = false;
                } else if (!falling) {
                    gravity = 0.8;
                    falling = true;

                }
                if (getBoundsLeft().intersects(t.getBounds())&&t.getId()!=Id.code) {
                    setVelX(1);
                    x = t.getX()+t.width;
                    facing =1;
                }
                if (getBoundsRight().intersects(t.getBounds())&&t.getId()!=Id.code) {
                    setVelX(-1);
                    x = t.getX()-width;
                    facing= 0;
                }

            }

        }
        if (falling){
            gravity+=0.1;
            setVelY((int)gravity);
        }
        if (velX!=0){
            frameDeloy++;
            if (frameDeloy>=6){
                frame++;
                if (frame>1){
                    frame=0;
                }
                frameDeloy=0;
            }
        }
    }
}

