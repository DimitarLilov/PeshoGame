package Input;


import Entity.Entity;
import Game.Game;
import Game.Id;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        for (int i = 0; i <Game.handler.entity.size() ; i++) {
            Entity en = Game.handler.entity.get(i);
            if (en.getId() == Id.player) {


                switch (key) {
                    case KeyEvent.VK_SPACE:
                        if (!en.jumping) {
                            en.jumping = true;
                            en.gravity = 8.0;
                        }
                        break;
                    case KeyEvent.VK_LEFT :
                        en.setVelX(-5);
                        en.facing = 0;
                        break;
                    case KeyEvent.VK_A :
                        en.setVelX(-5);
                        en.facing = 0;
                        break;
                    case KeyEvent.VK_RIGHT:
                        en.setVelX(5);
                        en.facing = 1;
                        break;
                    case KeyEvent.VK_D:
                        en.setVelX(5);
                        en.facing = 1;
                        break;
                    case  KeyEvent.VK_B:

                        en.smoking = true;
                        Game.countKoz=0;
                        break;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for (int i = 0; i <Game.handler.entity.size() ; i++) {
            Entity en = Game.handler.entity.get(i);
            if (en.getId() == Id.player) {
                switch (key) {
                    case KeyEvent.VK_UP:
                        en.setVelY(0);
                        break;
                    case KeyEvent.VK_LEFT:
                        en.setVelX(0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        en.setVelX(0);
                        break;
                    case KeyEvent.VK_A:
                        en.setVelX(0);
                        break;
                    case KeyEvent.VK_D:
                        en.setVelX(0);
                        break;

                }
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }


}
