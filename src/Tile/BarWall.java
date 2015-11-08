package Tile;


import Game.Game;
import Game.Handler;
import Game.Id;

import java.awt.*;

public class BarWall extends Tile {
    public BarWall(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x,y,width,height,solid,id,handler);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.barWall.getBufferImage(), x, y, width, height, null);

    }

    @Override
    public void tick() {

    }
}
