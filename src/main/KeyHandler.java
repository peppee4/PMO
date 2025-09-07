package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	GamePanel gp;
	
    public boolean upPressed,
                   downPressed,
                   leftPressed,
                   rightPressed,
                   ePressed;

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
        		gp.playSoundEffect(0);
                gp.getUi().minusCommandNum();
                if(gp.getUi().getCommandNum() < 0) {
                	gp.getUi().setCommandNum(2);
                }
            }
            if(code == KeyEvent.VK_S){
            	gp.playSoundEffect(0);
            	gp.getUi().plusCommandNum();
            	if(gp.getUi().getCommandNum() > 2) {
                	gp.getUi().setCommandNum(0);
                }
            }
        	if(code == KeyEvent.VK_ENTER){
        		if(gp.getUi().getCommandNum() == 0) {
                	gp.setGameState(gp.playState);
                }
        		if(gp.getUi().getCommandNum() == 1) {
        			gp.getUi().setPreviousState(gp.titleState);
                	gp.setGameState(gp.optionsState);
                }
        		if(gp.getUi().getCommandNum() == 2) {
                	System.exit(0);
                }
            }
        }
     // OPTIONS STATE - NUOVO!
        else if(gp.getGameState() == gp.optionsState) {
            
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.getUi().minusOptionsCommandNum();
            }
            
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.getUi().plusOptionsCommandNum();
            }
            
            if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                // Diminuisci valore opzione
                gp.getUi().changeOptionValue(false);
            }
            
            if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                // Aumenta valore opzione
                gp.getUi().changeOptionValue(true);
            }
            
            if(code == KeyEvent.VK_ENTER) {
                gp.getUi().selectOption(); // Gestisce il BACK
            }
            
            if(code == KeyEvent.VK_ESCAPE) {
                // Torna alla schermata precedente
            	gp.setGameState(gp.getUi().getPreviousState());
                gp.getUi().setOptionsCommandNum(0);
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
            else if(code == KeyEvent.VK_I){
            	gp.getUi().setPreviousState(gp.playState);
            	gp.setGameState(gp.optionsState);
            }
        }
        // Per quando siamo in pausa
        else if(gp.getGameState() == gp.pauseState) {
        	if(code == KeyEvent.VK_P){
            	gp.setGameState(gp.playState);
            }
        // Per quando dobbiamo interagire con un oggetto
        }else if(code == KeyEvent.VK_E && gp.getGameState() == gp.dialogueState){
        	ePressed = true;
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
        }else if(code == KeyEvent.VK_E){
        	ePressed = false;
        }
    }
    
}
