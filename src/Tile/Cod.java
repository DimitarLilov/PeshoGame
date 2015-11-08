package Tile;


import Game.Game;
import Game.Handler;
import Game.Id;
import gfx.Sprite;

import java.awt.*;

public class Cod extends Tile {
    public Sprite getCod;

    public Cod(int x, int y, int width, int height, boolean solid, Id id, Handler handler, Sprite getCod) {
        super(x,y,width,height,solid,id,handler);
        this.getCod = getCod;
    }
    @Override
    public void render(Graphics g) {
        if (!activated) g.drawImage(Game.cod.getBufferImage(), x, y, width, height, null);
        else g.drawImage(Game.blok.getBufferImage(),x,y,width,height,null);
    }

    @Override
    public void tick() {

    }
}
