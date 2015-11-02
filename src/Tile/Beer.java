package Tile;

import Game.Game;
import Game.Handler;
import Game.Id;
import Tile.Tile;
import gfx.Sprite;

import java.awt.*;

public class Beer extends Tile {

    public Beer(int x, int y, int width, int height, boolean solid,  Id id, Handler handler) {
        super(x,y,width,height,solid,id,handler);

    }
    @Override
    public void render(Graphics g) {
        g.drawImage(Game.beer[0].getBufferImage(), getX(), getY(), width, 60, null);
        if (noMoney)g.drawImage(Game.beer[1].getBufferImage(), getX()+120, getY()-80, width, 60, null);
    }

    @Override
    public void tick() {

    }
}
