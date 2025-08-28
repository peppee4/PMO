package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UiManager {

	GamePanel gp;
	Graphics2D g2;
	Font arial_40, arial_80B;
	
	public boolean gameFinished = false;
	public int commandNum = 0;
	
	public UiManager(GamePanel gp) {
		this.gp = gp;
		
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);
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
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 76F));
		String text = "The Pyros Stone";
		int x = this.getXForCenteredText(text);
		int y = gp.getTileSize() * 3;
		
		// SHADOW
		g2.setColor(Color.black);
		g2.drawString(text, x + 5, y + 5);
		// MAIN COLOR
		g2.setColor(Color.RED);
		g2.drawString(text, x, y);
		
		//BLUE BOY IMAGE
		x = gp.getScreenWidth()/2 - (gp.getTileSize()*2)/2;
		y  += gp.getTileSize()* 2;
		g2.drawImage(gp.getPlayer().getImageIdle(), x, y, gp.getTileSize()*2, gp.getTileSize()*2, null);
		
		
		
		// MENU
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
		
		text = "NEW GAME";
		x = this.getXForCenteredText(text);
		y += gp.getTileSize() * 3.5;
		g2.drawString(text, x, y);
		if(this.commandNum == 0) {
			g2.drawString(">", x - gp.getTileSize(), y);
		}
		
		text = "QUIT";
		x = this.getXForCenteredText(text);
		y += gp.getTileSize();
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
