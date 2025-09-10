package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Classe che implementa KeyListener per gestire gli input da tastiera
public class KeyHandler implements KeyListener{
	
	// Riferimento al pannello di gioco
	GamePanel gp;
	
	// Flag per tenere traccia dei tasti premuti
    private boolean upPressed,
                   downPressed,
                   leftPressed,
                   rightPressed,
                   ePressed;

    // Costruttore: riceve il pannello di gioco per poter interagire con lo stato del gioco
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
        
        // --- TITLE STATE (menu principale) ---
        if(gp.getGameState() == gp.titleState) {
        	// Navigazione su/giù nel menu
        	if(code == KeyEvent.VK_W){
        		gp.playSoundEffect(0);
                gp.getUi().minusCommandNum();
                if(gp.getUi().getCommandNum() < 0) {
                	gp.getUi().setCommandNum(3);
                }
            }
            if(code == KeyEvent.VK_S){
            	gp.playSoundEffect(0);
            	gp.getUi().plusCommandNum();
            	if(gp.getUi().getCommandNum() > 3) {
                	gp.getUi().setCommandNum(0);
                }
            }
            // Metodo per selezionare una voce
        	if(code == KeyEvent.VK_ENTER){
        		if(gp.getUi().getCommandNum() == 0) {
                	gp.setGameState(gp.playState);
                }
        		if(gp.getUi().getCommandNum() == 1) {
        			gp.getUi().setPreviousState(gp.titleState);
                	gp.setGameState(gp.optionsState);
                }
        		if(gp.getUi().getCommandNum() == 2) {
                	gp.setGameState(gp.tutorialState);
                }
        		if(gp.getUi().getCommandNum() == 3) {
        			// Termina il gioco chiudendo l'applicazione
                	System.exit(0);
                }
            }
        }
        // --- OPTIONS STATE (menu principale) ---
        else if(gp.getGameState() == gp.optionsState) {
            
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.getUi().minusOptionsCommandNum();
            }
            
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.getUi().plusOptionsCommandNum();
            }
            
            if(code == KeyEvent.VK_LEFT) {
                // Diminuisci valore opzione
                gp.getUi().changeOptionValue(false);
            }
            
            if(code == KeyEvent.VK_RIGHT) {
                // Aumenta valore opzione
                gp.getUi().changeOptionValue(true);
            }
            
            if(code == KeyEvent.VK_ENTER) {
            	// Gestisce il BACK
                gp.getUi().selectOption(); 
            }
            
            if(code == KeyEvent.VK_ESCAPE) {
                // Torna alla schermata precedente
            	gp.setGameState(gp.getUi().getPreviousState());
                gp.getUi().setOptionsCommandNum(0);
            }          
        }
        // --- OPTIONS CONTROL STATE (gestione controlli) ---
        else if(gp.getGameState() == gp.optionsControlState) {
        	if(code == KeyEvent.VK_ENTER) {
        		// Gestisce il BACK
                gp.setGameState(gp.optionsState); 
            }
        }
        // --- PLAY STATE (gioco attivo) ---
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
        	// Torna al menu opzioni
            else if(code == KeyEvent.VK_ESCAPE){
            	gp.getUi().setPreviousState(gp.playState);
            	gp.setGameState(gp.optionsState);
            }
        }
        // --- DIALOGUE STATE (interazione oggetti) ---
        else if(gp.getGameState() == gp.dialogueState){
        	
        	if(code == KeyEvent.VK_E){
        		ePressed = true;
             }
        }
        // --- GAME OVER STATE ---
        else if(gp.getGameState() == gp.gameOverState){
        	
        	if(code == KeyEvent.VK_W){
               gp.getUi().minusCommandNum();
               if(gp.getUi().getCommandNum() < 0) {
            	   gp.getUi().setCommandNum(1);
               }
               gp.playSoundEffect(0);
            }
            if(code == KeyEvent.VK_S){
            	gp.getUi().plusCommandNum();
                if(gp.getUi().getCommandNum() > 1) {
             	   gp.getUi().setCommandNum(0);
                }
                gp.playSoundEffect(0);
            }
            if(code == KeyEvent.VK_ENTER) {
                if(gp.getUi().getCommandNum() == 0) { 			// Restarta il game
                	gp.setFlagPlay(false);
                	gp.reset();
                	gp.setGameState(gp.playState);
                }else if(gp.getUi().getCommandNum() == 1) {     // Torna al menu principale
                	gp.setFlagTitle(false);
                	gp.setLevelNumber(1);							// Riporta al livello 1 per il prossimo gioco
                	gp.reset();
                	gp.setGameState(gp.titleState);
                }
            }
        }
        // --- NEXT LEVEL STATE ---
        else if(gp.getGameState() == gp.nextLevelState) {
            // Se abbiamo completato tutti i 3 livelli (levelNumber == 4), c'è solo "End Game"
            if(gp.getLevelNumber() >= 4) {
                // Non c'è navigazione nel menu, c'è solo una opzione
                if(code == KeyEvent.VK_ENTER) {
                    // End Game (torna al menu principale)
                    gp.setFlagPlay(false);
                    // Riporta al livello 1 per il prossimo gioco
                    gp.setLevelNumber(1); 
                    gp.reset();
                    gp.setGameState(gp.titleState);
                }
            } else {
                // Menu normale con "Next Level" e "End Game" 
                if(code == KeyEvent.VK_W){
                    gp.getUi().minusCommandNum();
                    if(gp.getUi().getCommandNum() < 0) {
                        gp.getUi().setCommandNum(1);
                    }
                    gp.playSoundEffect(0);
                }
                if(code == KeyEvent.VK_S){
                    gp.getUi().plusCommandNum();
                    if(gp.getUi().getCommandNum() > 1) {
                        gp.getUi().setCommandNum(0);
                    }
                    gp.playSoundEffect(0);
                }
                if(code == KeyEvent.VK_ENTER) {
                    if(gp.getUi().getCommandNum() == 0) {
                        // Next Level
                        gp.setFlagPlay(false);
                        gp.reset();
                        gp.setGameState(gp.playState);
                    }
                    if(gp.getUi().getCommandNum() == 1) {
                        // End Game
                        gp.setFlagPlay(false);
                        gp.setLevelNumber(1); // Riporta al livello 1 per il prossimo gioco
                        gp.reset();
                        gp.setGameState(gp.titleState);
                    }
                }
            }
        }
        // --- TUTORIAL STATE ---
        else if(gp.getGameState() == gp.tutorialState) {
        	if(code == KeyEvent.VK_ENTER) {
        		gp.setGameState(gp.titleState); // Gestisce il BACK
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
        }else if(code == KeyEvent.VK_E){
        	ePressed = false;
        }   
    }

    // ---- Getter ----

	public boolean isePressed() {
		return ePressed;
	}


	public boolean isUpPressed() {
		return upPressed;
	}


	public boolean isDownPressed() {
		return downPressed;
	}


	public boolean isLeftPressed() {
		return leftPressed;
	}


	public boolean isRightPressed() {
		return rightPressed;
	}
    
}
