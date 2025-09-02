package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UiManager {

	GamePanel gp;
	Graphics2D g2;
	Font arial_40, arial_80B;
	
	public boolean gameFinished = false;
	public int commandNum = 0;
	private int spriteCounter = 0;
	private int spriteNum = 1;
	private int xPlayer;
	private int yPlayer;
	
	public UiManager(GamePanel gp) {
		this.gp = gp;
		
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);
		
		this.xPlayer = gp.getScreenWidth()/2 - (gp.getTileSize()*2)/2;
		this.yPlayer  = (int) ((gp.getTileSize()* 2) +  (gp.getTileSize()* 3.35));
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		// TITLE STATE
		if(gp.getGameState() == gp.titleState) {
			drawTitleScreen();
			drawImagePlayer();
			
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
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
		String text = "The Pyros Stone";
		int x = this.getXForCenteredText(text);
		int y = (int) (gp.getTileSize() * 2.75);
		
		// SHADOW
		g2.setColor(Color.black);
		g2.drawString(text, x + 5, y + 5);
		// MAIN COLOR
		g2.setColor(Color.RED);
		g2.drawString(text, x, y);
		
		// CHRACTER IMAGE
		x = gp.getScreenWidth()/2 - (gp.getTileSize()*2)/2;
		y  += gp.getTileSize()* 2;
		
		//g2.drawImage(gp.getPlayer().getImageIdle(), x, y, gp.getTileSize()*2, gp.getTileSize()*2, null);
		//drawImagePlayer();
		
		
		// MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
		
		text = "NEW GAME";
		x = this.getXForCenteredText(text);
		y += gp.getTileSize() * 5;
		g2.drawString(text, x, y);
		if(this.commandNum == 0) {
			g2.drawString(">", x - gp.getTileSize(), y);
		}
		
		text = "QUIT";
		x = this.getXForCenteredText(text);
		y += gp.getTileSize() * 1.5;
		g2.drawString(text, x, y);
		if(this.commandNum == 1) {
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
		
		
		//g2.drawImage(gp.getPlayer().getImageIdle(), xPlayer, yPlayer, gp.getTileSize()*2, gp.getTileSize()*2, null);
		
		this.spriteCounter++;
		if(this.spriteCounter > 10) {
			if(this.spriteNum == 1) {
				this.spriteNum = 2;
			}else if(this.spriteNum == 2) {
				this.spriteNum = 1;
			}
		
			this.spriteCounter = 0;
		}
		
		this.xPlayer += 8;
		
		if(this.spriteNum == 1) {
			g2.drawImage(gp.getPlayer().getImageIdle(1), xPlayer, yPlayer, gp.getTileSize()*2, gp.getTileSize()*2, null);
		}else if(this.spriteNum == 2) {
			g2.drawImage(gp.getPlayer().getImageIdle(2), xPlayer, yPlayer, gp.getTileSize()*2, gp.getTileSize()*2, null);
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
}
