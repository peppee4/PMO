package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class SuperObject {
    public BufferedImage image;         // Immagine dell'oggetto
    public String name;                 // Nome dell'oggetto 
    public boolean collision = false;   // Indica se l'oggetto ha una collisione attiva
    public int worldX, worldY;          // Posizione dell'oggetto nel mondo di gioco

    // Metodo per disegnare l'oggetto
    public void draw(Graphics2D g2, GamePanel gp){
        int screenX = worldX - gp.player.getWorldX() + gp.player.getCenterX();
		int screenY = worldY - gp.player.getWorldY() + gp.player.getCenterY();
		
        // Disegna solo i tile che si trovano entro i confini dello schermo
		if(worldX + gp.getTileSize() > gp.player.getWorldX() - gp.player.getCenterX() &&
		   worldX - gp.getTileSize() < gp.player.getWorldX() + gp.player.getCenterX() &&
		   worldY + gp.getTileSize() > gp.player.getWorldY()- gp.player.getCenterY() &&
           	worldY - gp.getTileSize() < gp.player.getWorldY() + gp.player.getCenterY()) {
				
			// Disegna l’immagine della tile sullo schermo
		    g2.drawImage(image, screenX, screenY, 30, 30, null);
		}
    }
}
