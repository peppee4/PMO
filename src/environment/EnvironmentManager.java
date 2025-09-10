package environment;

import java.awt.Graphics2D;

import main.GamePanel;

public class EnvironmentManager {

	// Riferimento al GamePanel
	GamePanel gp;
	
	// Luce soffusa intorno al Player
	PlayerLighting lighting;
	
	// Costruttore della classe EnvironmentManager
	public EnvironmentManager(GamePanel gp) {
		this.gp = gp;
	}
	
	// Metodo per settare dimensioni raggio luce soffusa
	public void setLight(int value) {
		
		lighting = new PlayerLighting(gp, value);
	}
	
	// Metodo per disegnare la luce attorno al player
	public void draw(Graphics2D g2) {
		// Richiama il disegno della luce del player
		lighting.draw(g2);
	}
	
}
