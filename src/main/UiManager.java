package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Player;
import object.ObjHeart;
import object.ObjKey;
import object.SuperObject;

public class UiManager {

	GamePanel gp;
	Graphics2D g2;
	Font arial_40, arial_80B;
	BufferedImage heart_full, heart_half, heart_blank, key_image;
	
	private int commandNum = 0;
	private int spriteCounter = 0;
	private int spriteNum = 1;
	private int xPlayer;
	private int yPlayer;
	private int xMonster;
	private int yMonster;
	private int optionsCommandNum = 0;
	public int soundVolume = 50; 
	public int musicVolume = 50; 
	
	private int previousState = 0;
	
	
	private BufferedImage monsterImage1, monsterImage2; // Immagini del mostro
	
	public UiManager(GamePanel gp) {
		this.gp = gp;
		
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);
		
		loadMonsterImages();
		
		setxPlayer(gp.getScreenWidth()/2 - (gp.getTileSize()*2)/2);
		setyPlayer((int) ((gp.getTileSize()* 2) +  (gp.getTileSize()* 3.35)));
		
		setxMonster((int) (gp.getScreenWidth()/3.5 - (gp.getTileSize()*2)/2));
		setyMonster((int) ((gp.getTileSize()* 2) +  (gp.getTileSize()* 2.5)));
		
		// Creiamo i cuori che rappresentano la vita del player
		SuperObject heart = new ObjHeart(gp);
		heart_full = heart.getImage1();
		heart_half = heart.getImage2();
		heart_blank = heart.getImage3();
		
		// Creiamo l'immagine della chiave a schermo che informerà il player 
		// riguardo al numero di chiavi in suo possesso
		SuperObject key = new ObjKey(gp);
		key_image = key.getImage1();
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(arial_80B);
		g2.setColor(Color.white);
		
		// TITLE STATE
		if(gp.getGameState() == gp.titleState) {
			drawTitleScreen();			
		} 
		
		// OPTIONS STATE
		if(gp.getGameState() == gp.optionsState) {
			drawOptionsScreen();
		}
		
		// OPTIONS STATE
		if(gp.getGameState() == gp.optionsControlState) {
			drawOptionsControlScreen();
		}
		
		// PLAY STATE
		if(gp.getGameState() == gp.playState) {
			drawPlayerLife();
			drawKeys();
		}
		
		// FINESTRA DIALOGHI
		if(gp.getGameState() == gp.dialogueState) {
			drawDialogueScreen("Premi E per interagire");
		}
		// GAME OVER STATE
		if(gp.getGameState() == gp.gameOverState) {
			drawGameOverScreen();
		}
		// NEXT LEVEL STATE
		if(gp.getGameState() == gp.nextLevelState) {
			drawNextLevelState();
		}
	}
	
	private void drawNextLevelState() {
		// TODO Auto-generated method stub
		g2.setColor(new Color(0, 0, 0, 150)); // Nero semitrasparente
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
	}

	// Metodo per disegnare a schermo il numero di chiavi in possesso del player
	private void drawKeys() {
		// TODO Auto-generated method stub
	    
	    int y = (int)(gp.getTileSize() * 6.5) / 2;
	    int x = (int)(gp.getTileSize() * (28.5)) / 2;
        
	    int numberOfKeys = gp.getPlayer().getNumberOfKey();
	    
	    for(int i = 0; i < numberOfKeys; i++) {
	        x = (int)(gp.getTileSize() * (28.5 - i)) / 2;
	        g2.drawImage(key_image, x, y, null);
	    }
	}

	// Metodo per disegnare a schermo il numero di cuori del player
	private void drawPlayerLife() {
		
		int x = (int)(gp.getTileSize() * 10.5) / 2;
	    int y = (int)(gp.getTileSize() * 6.5) / 2;
	    
	    // Disegniamo prima i cuori vuoti
	    int tempX = x;
	    for(int i = 0; i < gp.getPlayer().getMaxLife(); i++) {
	        g2.drawImage(heart_blank, tempX, y, null);
	        tempX += gp.getTileSize() - 20;
	    }
	    
	    // Resettiamo le coordinate
	    tempX = x;
	    double currentLife = gp.getPlayer().getLife();
	    
	    // Disegniamo i cuori in base alla vita attuale
	    for(double life = 0; life < currentLife; life += 1.0) {
	        if(currentLife - life >= 1.0) {
	            // Cuore pieno (vita rimanente >= 1.0)
	            g2.drawImage(heart_full, tempX, y, null);
	        } else if(currentLife - life >= 0.5) {
	            // Cuore mezzo (vita rimanente >= 0.5 ma < 1.0)
	            g2.drawImage(heart_half, tempX, y, null);
	        }
	        // Se la vita rimanente < 0.5, non disegniamo nulla.
	        
	        tempX += gp.getTileSize() - 20;
	    }
	}

	private void drawOptionsControlScreen() {
		// TODO Auto-generated method stub
		g2.setColor(new Color(0, 0, 0, 150)); // Nero semitrasparente
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
		
		int x = (int)(gp.getTileSize() * 14.5) / 2;
	    int y = (gp.getTileSize() * 7) / 2;
	    int width = (gp.getScreenWidth() - (gp.getTileSize() * 4)) / 3;
	    int height = (gp.getTileSize() * 5);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
		
		drawSubWindow(x, y, width, height);
		
		// Colore e Grandezza del testo
		g2.setColor(Color.white);
		
		// TITOLO "CONTROLS" - centrato in alto nel rettangolo
		String text = "CONTROLS";
		int textX = x + (width / 2) - ((int)g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2);
		int textY = y + gp.getTileSize() / 2 + 10; // Un po' sotto il bordo superiore
		g2.drawString(text, textX, textY);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));

		// Posizione centrale del rettangolo per le colonne
		int centerX = x + (width / 2);
		int startY = textY + gp.getTileSize(); // Inizia sotto il titolo

		// COLONNA SINISTRA (Azioni) - centrata nella metà sinistra
		int leftColumnX = x + (width / 4); // Centro della metà sinistra
		textY = startY;

		// Centra ogni testo nella colonna sinistra
		text = "Move";
		textX = leftColumnX - ((int)g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2);
		g2.drawString(text, textX, textY);
		textY += gp.getTileSize() / 2;

		text = "Interact";
		textX = leftColumnX - ((int)g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2);
		g2.drawString(text, textX, textY);
		textY += gp.getTileSize() / 2;

		text = "Options";
		textX = leftColumnX - ((int)g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2);
		g2.drawString(text, textX, textY);
		textY += gp.getTileSize() / 2;

		text = "Back";
		textX = leftColumnX - ((int)g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2);
		g2.drawString(text, textX, textY);

		// COLONNA DESTRA (Tasti) - centrata nella metà destra
		int rightColumnX = x + (width * 3 / 4); // Centro della metà destra
		textY = startY;

		// Centra ogni testo nella colonna destra
		text = "WASD";
		textX = rightColumnX - ((int)g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2);
		g2.drawString(text, textX, textY);
		textY += gp.getTileSize() / 2;

		text = "E";
		textX = rightColumnX - ((int)g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2);
		g2.drawString(text, textX, textY);
		textY += gp.getTileSize() / 2;

		text = "I";
		textX = rightColumnX - ((int)g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2);
		g2.drawString(text, textX, textY);
		textY += gp.getTileSize() / 2;

		text = "ENTER";
		textX = rightColumnX - ((int)g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2);
		g2.drawString(text, textX, textY);

		// PULSANTE "BACK" - centrato in basso nel rettangolo
		text = "> BACK";
		textX = x + (width / 2) - ((int)g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2);
		textY = y + height - gp.getTileSize() / 3; // Un po' sopra il bordo inferiore
		g2.drawString(text, textX, textY);

	}

	private void drawGameOverScreen() {
		// TODO Auto-generated method stub
		// Sfondo semi-trasparente
		g2.setColor(new Color(0, 0, 0, 150)); // Nero semitrasparente
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70f));
		
		text = "Game Over";
		// Shadow
		g2.setColor(Color.black);
		x = getXForCenteredText(text);
		y = (gp.getTileSize() * 10) / 2;
		g2.drawString(text, x, y);
		
		g2.setColor(Color.white);
		g2.drawString(text, x - 4, y);
		
		// RETRY
		g2.setFont(g2.getFont().deriveFont(30f));
		text = "Retry";
		x = getXForCenteredText(text);
		y += gp.getTileSize() * 2;
		g2.drawString(text, x, y);
		if(this.commandNum == 0) {
			g2.drawString(">", x - 40, y);
		}
		
		// QUIT
		text = "Quit";
		x = getXForCenteredText(text);
		y += 45;
		g2.drawString(text, x, y);
		if(this.commandNum == 1) {
			g2.drawString(">", x - 40, y);
		}
	}

	public void drawTitleScreen(){
		
		// TITLE NAME
		try {
			g2.drawImage(ImageIO.read(getClass().getResourceAsStream("/titles/TitleImageBackground.png")), 0, 0, gp.getScreenWidth(), gp.getScreenHeight(), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 92F));
		String text = "The Pyros Stone";
		int x = this.getXForCenteredText(text);
		int y = (int) (gp.getTileSize() * 2.75); 

		// OMBRA (grigio scuro, spostata in basso a destra)
		g2.setColor(Color.black); // Nero semitrasparente
		g2.drawString(text, x + 6, y + 6);

		// CONTORNO NERO
		g2.setColor(Color.BLACK);
		int outline = 2;

		// 8 direzioni per il contorno
		g2.drawString(text, x, y - outline);
		g2.drawString(text, x, y + outline);
		g2.drawString(text, x - outline, y);
		g2.drawString(text, x + outline, y);
		g2.drawString(text, x - outline, y - outline);
		g2.drawString(text, x + outline, y - outline);
		g2.drawString(text, x - outline, y + outline);
		g2.drawString(text, x + outline, y + outline);

		// TESTO PRINCIPALE (rosso)
		g2.setColor(Color.RED);
		g2.drawString(text, x, y);
		
		drawImagePlayer();
		drawImageMonster();
				
		// MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
		
		text = "NEW GAME";
		x = this.getXForCenteredText(text);
		y += gp.getTileSize() * 6.4;
		g2.setColor(Color.black);
		g2.drawString(text, x + 5, y + 5);
		g2.setColor(Color.RED);
		g2.drawString(text, x, y);
		if(this.commandNum == 0) {
			g2.setColor(Color.black);
			g2.drawString(">", x - gp.getTileSize() + 5, y + 5);
			g2.setColor(Color.RED);
			g2.drawString(">", x - gp.getTileSize(), y);
		}
		
		text = "OPTIONS";
		x = this.getXForCenteredText(text);
		y += gp.getTileSize() * 1;
		g2.setColor(Color.black);
		g2.drawString(text, x + 5, y + 5);
		g2.setColor(Color.RED);
		g2.drawString(text, x, y);
		if(this.commandNum == 1) {
			g2.setColor(Color.black);
			g2.drawString(">", x - gp.getTileSize() + 5, y + 5);
			g2.setColor(Color.RED);
			g2.drawString(">", x - gp.getTileSize(), y);
		}
		
		text = "QUIT";
		x = this.getXForCenteredText(text);
		y += gp.getTileSize() * 1;
		g2.setColor(Color.black);
		g2.drawString(text, x + 5, y + 5);
		g2.setColor(Color.RED);
		g2.drawString(text, x, y);
		if(this.commandNum == 2) {
			g2.setColor(Color.black);
			g2.drawString(">", x - gp.getTileSize() + 5, y + 5);
			g2.setColor(Color.RED);
			g2.drawString(">", x - gp.getTileSize(), y);
		}
	}
	
	public int getXForCenteredText(String text) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.getScreenWidth()/2 - length/2;
		
		return x;
	}
	
	public void drawImagePlayer() {
		
		// Animazione sprite
		this.spriteCounter++;
		if(this.spriteCounter > 10) {
			if(this.spriteNum == 1) {
				this.spriteNum = 2;
			}else if(this.spriteNum == 2) {
				this.spriteNum = 1;
			}
		
			this.spriteCounter = 0;
		}
		
		// Movimento del player
		this.xPlayer += 8;
		
		// Controllo per il loop infinito
	    // Quando il player esce completamente dal lato destro, riappare dal lato sinistro
	    if(this.xPlayer > gp.getScreenWidth()) {
	        this.xPlayer = -gp.getTileSize(); // Riappare completamente fuori dal lato sinistro
	    }
	    
	    // Disegna il player
		if(this.spriteNum == 1) {
			g2.drawImage(gp.getPlayer().getImageIdle(1), xPlayer, yPlayer, gp.getTileSize()*2, gp.getTileSize()*2, null);
		}else if(this.spriteNum == 2) {
			g2.drawImage(gp.getPlayer().getImageIdle(2), xPlayer, yPlayer, gp.getTileSize()*2, gp.getTileSize()*2, null);
		}
	}
	
	public void drawImageMonster() {
		
		// Animazione sprite
		this.spriteCounter++;
		if(this.spriteCounter > 10) {
			if(this.spriteNum == 1) {
				this.spriteNum = 2;
			}else if(this.spriteNum == 2) {
				this.spriteNum = 1;
			}	
			this.spriteCounter = 0;
		}
				
		// Movimento del mostro
		this.xMonster += 8;
				
		// Controllo per il loop infinito
		// Quando il mostro esce completamente dal lato destro, riappare dal lato sinistro
		if(this.xMonster > gp.getScreenWidth()) {
			this.xMonster = -gp.getTileSize(); // Riappare completamente fuori dal lato sinistro
		}
			    
		// Disegna il mostro
		if(this.spriteNum == 1) {
			g2.drawImage(monsterImage1, xMonster, yMonster, gp.getTileSize()*3, gp.getTileSize()*3, null);
		}else if(this.spriteNum == 2) {
			g2.drawImage(monsterImage2, xMonster, yMonster, gp.getTileSize()*3, gp.getTileSize()*3, null);
		}
		
	}
	
	// Carichiamo le immagini una volta sola
	public void loadMonsterImages() {
	    try {
	        monsterImage1 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster/NormalMonster_right_1.png"));
	        monsterImage2 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster/NormalMonster_right_2.png"));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	// Metodo per stampare la versione del gioco nella schermata inziale 
	public void drawVersion() {
		int x, y;
    	String text;
    	
    	g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15F));
		text = "The Pyros Stone Alpha 0.1";
		x = 10; 
		y = gp.getScreenHeight() - 10; 
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
	}
	
	// Schermata delle opzioni
	public void drawOptionsScreen() {
		// Sfondo semi-trasparente
		
		g2.setColor(new Color(0, 0, 0, 150)); // Nero semitrasparente
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
		
		int x = (gp.getTileSize() * 11) / 2;
	    int y = (gp.getTileSize() * 7) / 2;
	    int width = (gp.getScreenWidth() - (gp.getTileSize() * 2)) / 2;
	    int height = (gp.getTileSize() * 5);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
		
		drawSubWindow(x, y, width, height);

		// Titolo "OPTIONS"
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
		String text = "OPTIONS";
		 x = this.getXForCenteredText(text);
		 y = (int) (gp.getTileSize() * 2);

		// Ombra del titolo
		g2.setColor(Color.BLACK);
		g2.drawString(text, x + 3, y + 3);
		// Titolo principale
		g2.setColor(Color.WHITE);
		g2.drawString(text, x, y);

		// Menu delle opzioni
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
		int startY = y + (int)(gp.getTileSize() * 2.5);
		int spacing = (int)(gp.getTileSize() * 0.8);

		// VOLUME MUSICA
		text = "MUSIC VOLUME: " + musicVolume + "%";
		x = this.getXForCenteredText(text);
		drawOptionItem(text, x, startY, 0);

		// VOLUME SUONI
		text = "SOUND VOLUME: " + soundVolume + "%";
		x = this.getXForCenteredText(text);
		drawOptionItem(text, x, startY + spacing, 1);

		// CONTROLS
		text = "CONTROLS";
		x = this.getXForCenteredText(text);
		drawOptionItem(text, x, startY + spacing * 2, 2);

		// BACK
		text = "BACK";
		x = this.getXForCenteredText(text);
		drawOptionItem(text, x, startY + spacing * 4, 3);

		// Istruzioni in basso
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 14F));
		text = "Usa le frecce DESTRA/SINISTRA per dimininuire o aumentare i valori";
		x = this.getXForCenteredText(text);
		g2.setColor(Color.LIGHT_GRAY);
		g2.drawString(text, x, (int)((gp.getTileSize()* 2) + (gp.getTileSize()* 6.8)));
	}
	
	private void drawOptionItem(String text, int x, int y, int itemIndex) {
		// Ombra
		g2.setColor(Color.BLACK);
		g2.drawString(text, x + 2, y + 2);
		
		// Testo principale
		if(this.optionsCommandNum == itemIndex) {
			g2.setColor(Color.YELLOW); // Evidenzia l'opzione selezionata
			// Freccia di selezione
			g2.setColor(Color.BLACK);
			g2.drawString(">", x - gp.getTileSize() + 2, y + 2);
			g2.setColor(Color.YELLOW);
			g2.drawString(">", x - gp.getTileSize(), y);
		} else {
			g2.setColor(Color.WHITE);
		}
		g2.drawString(text, x, y);
	}
	
	// Cambia il valore di un'opzione (chiamato quando si preme LEFT/RIGHT)
	public void changeOptionValue(boolean increase) {
	    switch(optionsCommandNum) {
	        case 0: // Music Volume
	            if(increase && musicVolume < 100) {
	                musicVolume += 10;
	                // AGGIUNGI QUESTA RIGA: applica il nuovo volume
	                gp.soundManager.setMusicVolume(musicVolume);
	            }
	            else if(!increase && musicVolume > 0) {
	                musicVolume -= 10;
	                // AGGIUNGI QUESTA RIGA: applica il nuovo volume
	                gp.soundManager.setMusicVolume(musicVolume);
	            }
	            break;
	        case 1: // Sound Volume
	        	if(increase && soundVolume < 100) {
	                soundVolume += 10;
	                // AGGIUNGI QUESTE RIGHE: applica il volume e riproduci suono di test
	                gp.soundManager.setSoundVolume(soundVolume);
	                gp.playSoundEffect(0); // Suono di test (cursor.wav)
	            }
	            else if(!increase && soundVolume > 0) {
	                soundVolume -= 10;
	                // AGGIUNGI QUESTE RIGHE: applica il volume e riproduci suono di test
	                gp.soundManager.setSoundVolume(soundVolume);
	                gp.playSoundEffect(0); // Suono di test (cursor.wav)
	            }
	        case 2: // Back
	            break;
	    }
	}
		
	// Gestisce l'azione quando si preme INVIO nel menu opzioni
	public void selectOption() {
		if(optionsCommandNum == 2) { // BACK
			gp.setGameState(gp.optionsControlState);
			optionsCommandNum = 0; // Reset selezione opzioni
		}	
		if(optionsCommandNum == 3) { // BACK
			gp.setGameState(previousState);
			optionsCommandNum = 0; // Reset selezione opzioni
		}			
	} 
	
	// Metodo per disegnare un dialogo all'interno di una finestra
	public void drawDialogueScreen(String text) {
	    int x = (gp.getTileSize() * 15) / 2;
	    int y = (gp.getTileSize() * 6) / 2;
	    int width = (gp.getScreenWidth() - (gp.getTileSize() * 4)) / 3;
	    int height = (gp.getTileSize() * 4) / 3;

	    drawSubWindow(x, y, width, height);
	    
	    g2.setColor(Color.white);
	    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 15F));

	    int textX = x + gp.getTileSize() - 40;
	    int textY = y + gp.getTileSize() - 20;
	    for (String line : text.split("\n")) {
	        g2.drawString(line, textX, textY);
	        textY += gp.getTileSize();
	    }
	}
	
	//
	public void drawSubWindow(int x, int y, int width, int height) {
		Color c = new Color(0,0,0,220);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
	}
	
	public int getPreviousState() {
	    return previousState;
	}

	public void setPreviousState(int previousState) {
	    this.previousState = previousState;
	}
			
	public int getOptionsCommandNum() {
		return optionsCommandNum;
	}
		
	public void setOptionsCommandNum(int optionsCommandNum) {
		this.optionsCommandNum = optionsCommandNum;
	}
		
	public void plusOptionsCommandNum() {
		this.optionsCommandNum++;
		if(this.optionsCommandNum > 3) { // 4 opzioni totali (0-3)
			this.optionsCommandNum = 0;
		}
		gp.playSoundEffect(0);
	}
	
	public void minusOptionsCommandNum() {
		this.optionsCommandNum--;
		if(this.optionsCommandNum < 0) {
			this.optionsCommandNum = 3;
		}
		gp.playSoundEffect(0);
	}
	
	public int getCommandNum() {
		return this.commandNum;
	}
	
	public void setCommandNum(int value) {
		
		this.commandNum = value;
	}
	
	public void minusCommandNum() {
		this.commandNum--;
	}
	
	public void plusCommandNum() {
		this.commandNum++;
	}

	public int getxPlayer() {
		return xPlayer;
	}

	public void setxPlayer(int xPlayer) {
		this.xPlayer = xPlayer;
	}

	public int getyPlayer() {
		return yPlayer;
	}

	public void setyPlayer(int yPlayer) {
		this.yPlayer = yPlayer;
	}

	public int getxMonster() {
		return xMonster;
	}

	public void setxMonster(int xMonster) {
		this.xMonster = xMonster;
	}

	public int getyMonster() {
		return yMonster;
	}

	public void setyMonster(int yMonster) {
		this.yMonster = yMonster;
	}
	
}
