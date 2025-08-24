package entity;

import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Monsters extends Entity {
    public BufferedImage image;        // Immagine del mostro
    public int worldX, worldY;         // Posizione del mostro nel mondo di gioco
    private String name;               // Nome del mostro
    
    // Costruttore
    public Monsters(String name) {
        this.name = name;
    }

    public void draw(Graphics2D g2, GamePanel gp){
        int screenX = worldX - gp.player.getWorldX() + gp.player.getCenterX();
		int screenY = worldY - gp.player.getWorldY() + gp.player.getCenterY();
		
        // Disegna solo i tile che si trovano entro i confini dello schermo
		if(worldX + gp.getTileSize() > gp.player.getWorldX() - gp.player.getCenterX() &&
		    worldX - gp.getTileSize() < gp.player.getWorldX() + gp.player.getCenterX() &&
		    worldY + gp.getTileSize() > gp.player.getWorldY()- gp.player.getCenterY() &&
            worldY - gp.getTileSize() < gp.player.getWorldY() + gp.player.getCenterY()) {
			
            switch(this.getDirection()) {
			case "up":
				if(this.getSpriteNum() == 1) {
					image = up1;
				}else if(this.getSpriteNum() == 2) {
					image = up2;
				}
				break;
			case "down":
				if(this.getSpriteNum() == 1) {
					image = down1;
				}else if(this.getSpriteNum() == 2) {
					image = down2;
				}
				break;
			case "left":
				if(this.getSpriteNum() == 1) {
					image = left1;
				}else if(this.getSpriteNum() == 2) {
					image = left2;
				}
				break;
			case "right":
				if(this.getSpriteNum() == 1) {
					image = right1;
				}else if(this.getSpriteNum() == 2) {
					image = right2;
				}
				break;
			default:
				image = stop;
				break;
		}

			// Disegna l’immagine della tile sullo schermo
		    g2.drawImage(image, screenX, screenY, 60, 60, null);
		}
    }
}
