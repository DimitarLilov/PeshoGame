package Game;

import Entity.Entity;
import Entity.Player;
import Entity.Bugs;
import Tile.Beer;
import Entity.Koz;
import Tile.Tile;
import Tile.Code;
import Tile.*;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Handler {
    public LinkedList<Entity> entity = new LinkedList<Entity>();
    public LinkedList<Tile> tile = new LinkedList<Tile>();
    public Handler(){

    }

    public void render(Graphics g){
        for (int i = 0; i <entity.size() ; i++) {
            Entity e =entity.get(i);
            if (Game.getVisibleArea()!=null && e.getBounds().intersects(Game.getVisibleArea())&&e.getId()!=Id.particle)e.render(g);
        }
        for (int i = 0; i < tile.size(); i++) {
            Tile t = tile.get(i);
            if (Game.getVisibleArea()!=null && t.getBounds().intersects(Game.getVisibleArea()))t.render(g);
        }
        for (int i = 0; i <entity.size() ; i++) {
            Entity e =entity.get(i);
            if (Game.getVisibleArea()!=null && e.getBounds().intersects(Game.getVisibleArea())&&e.getId()==Id.particle)e.render(g);
        }

    }
    public void tick(){
        for (int i = 0; i <entity.size() ; i++) {
            Entity e =entity.get(i);
            e.tick();
        }
        for (int i = 0; i < tile.size(); i++) {
            Tile t = tile.get(i);
            if (Game.getVisibleArea()!=null && t.getBounds().intersects(Game.getVisibleArea()))t.tick();
        }
    }
    public void addEntity(Entity e){
        entity.add(e);

    }
    public void removeEntity(Entity e){
        entity.remove(e);
    }
    public void addTile(Tile t){
        tile.add(t);
    }
    public void removeTile(Tile t){
        tile.remove(t);
    }
    public void createLevel(BufferedImage level){
        int width = level.getWidth();
        int height = level.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = level.getRGB(x,y);

                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if (red == 0 && green == 0 && blue == 0)addTile(new Wall(x*60,y*60,60,60,true,Id.wall,this));
                if (red == 234 && green == 255 && blue == 0)addTile(new Code(x*60,y*60,60,60,true,Id.code,this,Game.blok));
                if (red == 255 && green == 255 && blue == 0)addTile(new Coins(x*60,y*60,60,60,true,Id.coins,this));
                if (red == 255 && green == 100 && blue == 0)addTile(new Beer(x * 60, y * 60, 60, 60, true, Id.beer, this));
                if (red == 207 && green == 69 && blue == 0)addTile(new Floor(x*60,y*60,60,60,true,Id.wall,this));
                if (red == 100 && green == 100 && blue == 100)addTile(new BarWall(x*60,y*60,60,60,true,Id.wall,this));
                if (red == 0 && green == 255 && blue == 0)addTile(new Blok(x*60,y*60,60,60,true,Id.blok,this));
                if (red == 0 && green == 255 && blue == 255)addTile(new FinalLevel(x*60,y*60,60,60,true,Id.finalLevel,this));
                if (red == 136 && green == 0 && blue == 0)addTile(new Bar(x*60,y*60,60*12,60,true,Id.bar,this));
                if (red == 255 && green == 0 && blue == 0)addEntity(new Bugs(x*60,y*60,60,60,Id.bugs,this));
                if (red == 67 && green == 67 && blue == 67)addEntity(new Koz(x * 60, y * 60, 59, 59, Id.koz, this));
                if (red == 0 && green == 0 && blue == 255) addEntity(new Player(x*69,y*69,69,69, Id.player,this));
            }
            
        }
    }
public void clearLevel(){
    entity.clear();
    tile.clear();
}


}
