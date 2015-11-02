package gfx.gul;

import Game.Game;

import java.awt.*;

public class Launcher {

    public Button[] buttons;

    public Launcher(){
        buttons=new Button[2];

        buttons[0] = new Button(500,200,100,100,"Start Game");
        buttons[1] = new Button(500,400,100,100,"Exit Game");
    }
    public void render(Graphics g){
        g.setColor(Color.cyan);
        g.fillRect(0,0,Game.getFrameWight(), Game.getFrameHeight());
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].render(g);
        }
    }
}
