package gfx;

import java.awt.image.BufferedImage;

public class Sprite {
    public BufferedImage image;

    public  Sprite(int x, int y,SpriteSheet sheet){
        image = sheet.getSheet(x,y);
    }
    public  BufferedImage getBufferImage(){
        return image;
    }
}
