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
	
	
	private BufferedImage monsterImage1, monsterImage2; // Immagini del mostro
	
	public UiManager(GamePanel gp) {
		this.gp = gp;
		
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);
		
		loadMonsterImages();
		
		setxPlayer(gp.getScreenWidth()/2 - (gp.getTileSize()*2)/2);
		setyPlayer((int) ((gp.getTileSize()* 2) +  (gp.getTileSize()* 3.35)));
		
		setxMonster((int) (gp.getScreenWidth()/3.5 - (gp.getTileSize()*2)/2));
		setyMonster((int) ((gp.getTileSize()* 2) +  (gp.getTileSize()* 3.35)));
		
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		// TITLE STATE
		if(gp.getGameState() == gp.titleState) {
			drawTitleScreen();			
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
		String text = "The Pyros Stone";
		int x = this.getXForCenteredText(text);
		int y = (int) (gp.getTileSize() * 2.75);
		
		// SHADOW
		g2.setColor(Color.black);
		g2.drawString(text, x + 5, y + 5);
		// MAIN COLOR
		g2.setColor(Color.RED);
		g2.drawString(text, x, y);
		
		drawImagePlayer();
		drawImageMonster();
				
		// MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
		
		text = "NEW GAME";
		x = this.getXForCenteredText(text);
		y += gp.getTileSize() * 6.5;
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
		
		text = "QUIT";
		x = this.getXForCenteredText(text);
		y += gp.getTileSize() * 1.5;
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
			g2.drawImage(monsterImage1, xMonster, yMonster, gp.getTileSize()*2, gp.getTileSize()*2, null);
		}else if(this.spriteNum == 2) {
			g2.drawImage(monsterImage2, xMonster, yMonster, gp.getTileSize()*2, gp.getTileSize()*2, null);
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
