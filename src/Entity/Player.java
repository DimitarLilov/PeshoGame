package Entity;

import Game.Game;
import Game.Handler;
import Game.Id;
import Tile.Tile;
import Tile.Cod;

import java.awt.*;

public class Player extends Entity {
    public int frame =0;
    public int frameDeloy =0;

    public Player(int x , int y, int width, int height,  Id id, Handler handler){
        super(x,y,width,height,id,handler);
    }

    public void render(Graphics g) {
        if (facing == 0){
            g.drawImage(Game.player[3+frame].getBufferImage(),x,y,width,height,null);
        } else if (facing ==1){
            g.drawImage(Game.player[frame].getBufferImage(),x,y,width,height,null);
        }

    }

    public void tick() {
        x+=velX;
        y+=velY;

        for (int i = 0; i < handler.tile.size(); i++) {
            Tile t = handler.tile.get(i);
            if (t.isSolid()){
                if (getBoundsTop().intersects(t.getBounds())&&t.getId()!=Id.cod&&t.getId()!=Id.blok){
                    setVelX(0);
                    if (jumping){
                        jumping = false;
                        gravity = 0.8;
                        falling = true;
                    }
                }
                if (getBoundsBottom().intersects(t.getBounds())&&t.getId()!=Id.cod&&t.getId()!=Id.blok) {
                    setVelY(0);
                    if (falling) falling = false;
                }else {
                    if (!falling && !jumping){
                        gravity = 0.8;
                        falling = true;
                    }
                }
                if (getBoundsLeft().intersects(t.getBounds())&&t.getId()!=Id.cod&&t.getId()!=Id.blok){
                    setVelX(0);
                    x = t.getX()+t.width;
                }
                if (getBoundsRight().intersects(t.getBounds())&&t.getId()!=Id.cod&&t.getId()!=Id.blok){
                    setVelX(0);
                    x = t.getX()-t.width;
                }
                if (getBounds().intersects(t.getBounds())&&t.getId()==Id.cod&&t.activated==false){
                    t.activated = true;
                        Game.cods++;


                }
                for (int j = 0; j < handler.entity.size(); j++) {
                    Entity e = handler.entity.get(j);
                    if (e.getId()==Id.bugs){
                        if (getBounds().intersects(e.getBounds())){
                            t.activated=false;
                            Game.cods = 0;
                        }
                    }
                }
            }
        }


        if (jumping){
            gravity -=0.1;
            setVelY((int)-gravity);
            if (gravity <= 0.0){
                jumping = false;
                falling = true;
            }
        }
        if (falling){
            gravity+=0.1;
            setVelY((int)gravity);
        }
        if (velX!=0){
            frameDeloy++;
            if (frameDeloy>=10){
                frame++;
                if (frame>2){
                    frame=0;
                }
                frameDeloy=0;
            }
        }

    }

}
