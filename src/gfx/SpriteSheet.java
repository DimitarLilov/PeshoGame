package gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
    private BufferedImage sheet;

    public  SpriteSheet(String path){
        try {
            sheet = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public  BufferedImage getSheet(int x, int y){
        return  sheet.getSubimage(x*69-69, y*69-69,69,69);

    }
}
