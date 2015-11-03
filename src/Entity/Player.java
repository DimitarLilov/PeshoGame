package Entity;

import Game.Game;
import Game.Handler;
import Game.Id;
import Tile.Tile;
import Tile.Trail;

import java.awt.*;
import java.util.Random;

public class Player extends Entity {
    public int frame =0;
    public int frameDeloy =0;
    public int particleDeloy =0;
    public boolean smokingKoz;
    public boolean invincible = false;
    public int stepSmoking ;
    private Random random;

    public Player(int x , int y, int width, int height,  Id id, Handler handler){
        super(x,y,width,height,id,handler);
        random = new Random();
    }

    public void render(Graphics g) {

        if (smokingKoz&& smoking){
            stepSmoking++;
            if (stepSmoking % 500 == 0)
            {
                smokingKoz = false;
                smoking = false;
                invincible = true;
            }else {
                g.drawImage(Game.smoking.getBufferImage(),x,y,width,height,null);
            }

        } else if (facing == 0){
            g.drawImage(Game.player[3+frame].getBufferImage(),x,y,width,height,null);
        } else if (facing ==1){
            g.drawImage(Game.player[frame].getBufferImage(),x,y,width,height,null);
        }
    }

    public void tick() {
        x+=velX;
        y+=velY;
        if (invincible) {

            if (facing == 0)
                handler.addTile(new Trail(getX(), getY(), getWidth(), getHeight(), false, Id.trail, handler, Game.player[3 + frame].getBufferImage()));
            else if (facing == 1)
                handler.addTile(new Trail(getX(), getY(), getWidth(), getHeight(), false, Id.trail, handler, Game.player[frame].getBufferImage()));
            particleDeloy++;
            if (particleDeloy >= 3) {
                handler.addEntity(new Particle(getX() + random.nextInt(getWidth()), getY() + random.nextInt(getHeight()), 10, 10, Id.particle, handler));
                particleDeloy = 0;
            }
        }

        for (int i = 0; i < handler.tile.size(); i++) {
            Tile t = handler.tile.get(i);
            if (t.isSolid()){
                if (getBoundsTop().intersects(t.getBounds())&&t.getId()!=Id.code&&t.getId()!=Id.coins&&t.getId()!=Id.beer&&t.getId()!=Id.blok){
                    setVelX(0);
                    if (jumping){
                        jumping = false;
                        gravity = 0.8;
                        falling = true;
                    }
                }
                if (getBoundsBottom().intersects(t.getBounds())&&t.getId()!=Id.code&&t.getId()!=Id.coins&&t.getId()!=Id.beer&&t.getId()!=Id.blok) {
                    setVelY(0);
                    if (falling) falling = false;
                }else {
                    if (!falling && !jumping){
                        gravity = 0.8;
                        falling = true;
                    }
                }
                if (getBoundsLeft().intersects(t.getBounds())&&t.getId()!=Id.code&&t.getId()!=Id.coins&&t.getId()!=Id.beer&&t.getId()!=Id.blok){
                    setVelX(0);
                    x = t.getX()+t.width;
                }
                if (getBoundsRight().intersects(t.getBounds())&&t.getId()!=Id.code&&t.getId()!=Id.coins&&t.getId()!=Id.beer&&t.getId()!=Id.blok ){
                    setVelX(0);
                    x = t.getX()-width;
                    if (t.getId() == Id.finalLevel && Game.countCode >= (Game.elemetn-1)){
                        x = t.getX()-t.width;
                    }
                }
                if (getBounds().intersects(t.getBounds())&&t.getId()==Id.code&&!t.activated){
                    t.activated = true;

                        Game.countCode++;


                }
                if (getBounds().intersects(t.getBounds())&&t.getId()==Id.coins){
                    Game.coins++;
                    t.die();

                }
                if (getBounds().intersects(t.getBounds())&&t.getId()==Id.beer){
                    if (Game.coins >=15){
                        Game.coins -=15;
                        Game.drinkBeer = true;
                        int tpX = getX();
                        int tpY= getY();
                        width += (width/4);
                        height += (height/4);
                        setX(tpX - width );
                        setY(tpY - height);
                        t.die();
                    } else {
                        t.noMoney=true;
                    }

                }


                if (getBounds().intersects(t.getBounds())){
                    if (t.getId() == Id.finalLevel && Game.countCode >= (Game.elemetn-1)){
                        x = t.getX()-t.width;
                        System.out.println(Game.finalPrint);
                        smokingKoz = false;
                        smoking = false;
                        Game.coins = 0;
                        Game.level++;
                        Game.switchLevel();
                    }
                }
                for (int j = 0; j < handler.entity.size(); j++) {
                    Entity e = handler.entity.get(j);
                    if (e.getId()==Id.bugs){
                        if (getBounds().intersects(e.getBounds())){
                            t.activated=false;
                            Game.countCode = 0;
                        }
                    }
                    if (e.getId()==Id.koz){
                        if (getBounds().intersects(e.getBounds())){
                            smokingKoz=true;
                            Game.countKoz++;
                            e.die();
                        }
                    }

                }
            }
        }


        if (jumping){
            gravity -=0.14;
            setVelY((int)-gravity);
            if (gravity <= 0.0){
                jumping = false;
                falling = true;
            }
        }
        if (falling){
            gravity+=0.14;
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
