package Tile;


import Game.Game;
import Game.Handler;
import Game.Id;

import java.awt.*;

public class Bar extends Tile {
    public Bar(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x,y,width,height,solid,id,handler);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.bar[0].getBufferImage(), getX(), getY(), width, 60, null);
        g.drawImage(Game.bar[1].getBufferImage(), getX(), getY()-60, width/12, 60, null);
        g.drawImage(Game.bar[2].getBufferImage(), getX(), getY()-120, width/12, 60, null);
        g.drawImage(Game.bar[3].getBufferImage(), getX()+480, getY()-60, width/12, 60, null);
        g.drawImage(Game.bar[5].getBufferImage(), getX()+540, getY()-60, width/12, 60, null);
        g.drawImage(Game.bar[5].getBufferImage(), getX()+600, getY()-60, width/12, 60, null);
        g.drawImage(Game.bar[4].getBufferImage(), getX()+660, getY()-60, width/12, 60, null);
        g.drawImage(Game.bar[6].getBufferImage(), getX()+540, getY()-120, width/12, 60, null);
        g.drawImage(Game.bar[7].getBufferImage(), getX()+600, getY()-120, width/12, 60, null);
        g.drawImage(Game.bar[8].getBufferImage(), getX()+660, getY()-120, width/12, 60, null);







    }

    @Override
    public void tick() {

    }
}
