package environment;

import java.awt.Graphics2D;

import main.GamePanel;

public class EnvironmentManager {

	GamePanel gp;
	PlayerLighting lighting;
	
	public EnvironmentManager(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setup() {
		// Crea l'effetto luce attorno al player con raggio 350
		lighting = new PlayerLighting(gp, 250);
	}
	
	public void draw(Graphics2D g2) {
		// Richiama il disegno della luce del player
		lighting.draw(g2);
	}
}
