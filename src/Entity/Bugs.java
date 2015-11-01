package Entity;

import Game.Game;
import Game.Handler;
import Game.Id;
import Tile.Tile;


import java.awt.*;
import java.util.Random;

public class Bugs extends Entity {

    private Random random = new Random();
    private int frame;
    private int frameDeloy;

    public Bugs(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
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
            g.drawImage(Game.bugs[3+frame].getBufferImage(),x,y,width,height,null);
        } else if (facing ==1){
            g.drawImage(Game.bugs[frame].getBufferImage(),x,y,width,height,null);
        }
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        for (int i = 0; i < handler.tile.size(); i++) {
            Tile t = handler.tile.get(i);
            if (t.isSolid()) {
                if (getBoundsBottom().intersects(t.getBounds())&&t.getId()!=Id.cod) {
                    setVelY(0);
                    if (falling) falling = false;
                } else if (!falling) {
                    gravity = 0.8;
                    falling = true;

                }
                if (getBoundsLeft().intersects(t.getBounds())&&t.getId()!=Id.cod) {
                    setVelX(1);

                    facing =1;
                }
                if (getBoundsRight().intersects(t.getBounds())&&t.getId()!=Id.cod) {
                    setVelX(-1);

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

