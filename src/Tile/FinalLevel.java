package Tile;


import Game.Game;
import Game.Handler;
import Game.Id;

import java.awt.*;

public class FinalLevel extends Tile {
    public FinalLevel(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x,y,width,height,solid,id,handler);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.finalLevel[1].getBufferImage(), getX(), getY(), width, 60, null);
        g.drawImage(Game.finalLevel[3].getBufferImage(), getX()+60, getY(), width, 60, null);
        g.drawImage(Game.finalLevel[6].getBufferImage(), getX()+120, getY(), width, 60, null);

        g.drawImage(Game.finalLevel[2].getBufferImage(), getX(), getY()-60, width, 60, null);
        g.drawImage(Game.finalLevel[7].getBufferImage(), getX()+60, getY()-60, width, 60, null);
        g.drawImage(Game.finalLevel[5].getBufferImage(), getX()+120, getY()-60, width, 60, null);

        g.drawImage(Game.finalLevel[4].getBufferImage(), getX(), getY()-120, width, 60, null);
        g.drawImage(Game.finalLevel[0].getBufferImage(), getX()+60, getY()-120, width, 60, null);
        g.drawImage(Game.finalLevel[5].getBufferImage(), getX()+120, getY()-120, width, 60, null);

        g.drawImage(Game.finalLevel[9].getBufferImage(), getX(), getY()-180, width, 60, null);
        g.drawImage(Game.finalLevel[8].getBufferImage(), getX()-60, getY()-180, width, 60, null);
        g.drawImage(Game.finalLevel[9].getBufferImage(), getX()+60, getY()-180, width, 60, null);
        g.drawImage(Game.finalLevel[9].getBufferImage(), getX()+120, getY()-180, width, 60, null);



    }

    @Override
    public void tick() {

    }
}
