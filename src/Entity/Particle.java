package Entity;

import Game.Game;
import Game.Handler;
import Game.Id;
import Tile.Tile;


import java.awt.*;

public class Particle extends Entity {
    private int frame = 0;
    private int frameDelay = 0;

    private boolean fading = false;
    public Particle(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);

    }

    @Override
    public void render(Graphics g) {
        if (frame >= Game.cannabis.length-1) fading = true;
        if (frame >=7&&fading) die();
        if (!fading) g.drawImage(Game.cannabis[frame].getBufferImage(),getX(),getY(),getWidth(),getHeight(),null);
        else g.drawImage(Game.cannabis[Game.cannabis.length - (frame-(Game.cannabis.length-2))].getBufferImage(),getX(),getY(),getWidth(),getHeight(),null);
    }

    @Override
    public void tick() {
        frameDelay++;
        if (frameDelay >=3){
            frame++;
            frameDelay =0;
        }

    }
}

