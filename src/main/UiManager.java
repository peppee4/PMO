package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UiManager {

	GamePanel gp;
	Graphics2D g2;
	Font arial_40, arial_80B;
	
	public boolean gameFinished = false;
	
	public UiManager(GamePanel gp) {
		this.gp = gp;
		
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		if(gp.getGameState() == gp.playState) {
			
		}
		
		if(gp.getGameState() == gp.pauseState) {
			drawPauseScreen();
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
}
