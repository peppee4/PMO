package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	GamePanel gp;
	
    public boolean upPressed,
                   downPressed,
                   leftPressed,
                   rightPressed,
                   enterPressed;

    public KeyHandler(GamePanel gp) {
    	
    	this.gp = gp;
    }

     
    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    // Metodo per dire cosa fare quando viene premuto un bottone
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        
        if(gp.getGameState() == gp.titleState) {
        	if(code == KeyEvent.VK_W){
                gp.ui.minusCommandNum();
                if(gp.ui.getCommandNum() < 0) {
                	gp.ui.setCommandNum(1);
                }
            }
            if(code == KeyEvent.VK_S){
            	gp.ui.plusCommandNum();
            	if(gp.ui.getCommandNum() > 1) {
                	gp.ui.setCommandNum(0);
                }
            }
        	if(code == KeyEvent.VK_ENTER){
        		if(gp.ui.getCommandNum() == 0) {
                	gp.setGameState(gp.playState);
                }
        		if(gp.ui.getCommandNum() == 1) {
                	System.exit(0);
                }
            }
        }
        else if(gp.getGameState() == gp.playState) {
        	if(code == KeyEvent.VK_W){
                upPressed = true;
            }
            else if(code == KeyEvent.VK_S){
                downPressed = true;
            }
            else if(code == KeyEvent.VK_A){
                leftPressed = true;
            }
            else if(code == KeyEvent.VK_D){
                rightPressed = true;
            }
            else if(code == KeyEvent.VK_P){
            	gp.setGameState(gp.pauseState);
            }
        }
        else if(gp.getGameState() == gp.pauseState) {
        	if(code == KeyEvent.VK_P){
            	gp.setGameState(gp.playState);
            }
        }
    }

    // Metodo per dire cosa fare quando viene rilasciato un bottone 
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = false;
        }else if(code == KeyEvent.VK_S){
            downPressed = false;
        }else if(code == KeyEvent.VK_A){
            leftPressed = false;
        }else if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
    }
    
}
