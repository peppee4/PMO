package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UiManager {

	GamePanel gp;
	Graphics2D g2;
	Font arial_40, arial_80B;
	
	private boolean gameFinished = false;
	private int commandNum = 0;
	private int spriteCounter = 0;
	private int spriteNum = 1;
	private int xPlayer;
	private int yPlayer;
	private int xMonster;
	private int yMonster;
	private int optionsCommandNum = 0;
	private int soundVolume = 50; 
	private int musicVolume = 50; 
	private int effectsVolume = 50; 
	private boolean fullscreen = false; 
	
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
		
		// PLAY STATE
		if(gp.getGameState() == gp.playState) {
			
		}
		
		// PAUSE STATE
		if(gp.getGameState() == gp.pauseState) {
			drawPauseScreen();
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
		String text = "The Labyrinth";
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
	
	public void drawPauseScreen() {
		
		String text = "PAUSED";
		int x = this.getXForCenteredText(text);
		int y = gp.getScreenHeight() / 2;
		
		g2.drawString(text, x, y);
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
	        monsterImage1 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster_right_1.png"));
	        monsterImage2 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster_right_2.png"));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	// Metodo per stampare la versione del gioco nella schermata inziale 
	public void drawVersion() {
		int x, y;
    	String text;
    	
    	g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15F));
		text = "Labyrinth Alpha 0.1";
		x = 10; 
		y = gp.getScreenHeight() - 10; 
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
	}
	
	// NUOVO METODO: Schermata delle opzioni
	public void drawOptionsScreen() {
		// Sfondo semi-trasparente
		g2.setColor(new Color(0, 0, 0, 150)); // Nero semitrasparente
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
			
		// Titolo "OPTIONS"
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
		String text = "OPTIONS";
		int x = this.getXForCenteredText(text);
		int y = (int) (gp.getTileSize() * 2);
			
		// Ombra del titolo
		g2.setColor(Color.BLACK);
		g2.drawString(text, x + 3, y + 3);
		// Titolo principale
		g2.setColor(Color.WHITE);
		g2.drawString(text, x, y);
			
		// Menu delle opzioni
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
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
			
		// VOLUME EFFETTI
		text = "EFFECTS VOLUME: " + effectsVolume + "%";
		x = this.getXForCenteredText(text);
		drawOptionItem(text, x, startY + spacing * 2, 2);
			
		// FULLSCREEN
		text = "FULLSCREEN: " + (fullscreen ? "ON" : "OFF");
		x = this.getXForCenteredText(text);
		drawOptionItem(text, x, startY + spacing * 3, 3);
			
		// BACK
		text = "BACK";
		x = this.getXForCenteredText(text);
		drawOptionItem(text, x, startY + spacing * 4, 4);
			
		// Istruzioni in basso
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 14F));
		text = "Usa le frecce DESTRA/SINISTRA per dimininuire o aumentare le opzioni";
		x = this.getXForCenteredText(text);
		g2.setColor(Color.LIGHT_GRAY);
		g2.drawString(text, x, (int)((gp.getTileSize()* 2) +  (gp.getTileSize()* 6.8)));
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
				if(increase && musicVolume < 100) 
					musicVolume += 10;
				else if(!increase && musicVolume > 0) 
					musicVolume -= 10;
				break;
			case 1: // Sound Volume
				if(increase && soundVolume < 100) 
					soundVolume += 10;
				else if(!increase && soundVolume > 0) 
					soundVolume -= 10;
				break;
			case 2: // Effects Volume
				if(increase && effectsVolume < 100) 
					effectsVolume += 10;
				else if(!increase && effectsVolume > 0) 
					effectsVolume -= 10;
				break;
			case 3: // Fullscreen
				fullscreen = !fullscreen;
				break;
			case 4: // Back - non fa nulla qui, gestito nel KeyHandler
				break;
		}
	}
		
	// Gestisce l'azione quando si preme INVIO nel menu opzioni
	public void selectOption() {
		if(optionsCommandNum == 4) { // BACK
			gp.setGameState(previousState);
			optionsCommandNum = 0; // Reset selezione opzioni
		}
			
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
		if(this.optionsCommandNum > 4) { // 5 opzioni totali (0-4)
			this.optionsCommandNum = 0;
		}
	}
	
	public void minusOptionsCommandNum() {
		this.optionsCommandNum--;
		if(this.optionsCommandNum < 0) {
			this.optionsCommandNum = 4;
		}
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
