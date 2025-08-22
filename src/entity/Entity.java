package entity;

import java.awt.image.BufferedImage;

/**
 * Entità di base del gioco.
 * Definisce posizione, velocità, direzione e riferimenti ai frame sprite
 * per l’animazione nelle quattro direzioni.
 */

public abstract class Entity {
	
	public int worldX, 		// Coordinata orizzontale
			   worldY;		// Coordinata verticale
	public int speed;	// Velocità di movimento
	
	
	
	// Frame dell’animazione per ciascuna direzione
	public BufferedImage up1, 
					     up2, 
					     down1, 
					     down2, 
					     left1, 
					     left2, 
					     right1, 
					     right2,
					     stop;
	public String direction;		// Direzione corrente
	public int spriteCounter = 0;	// Contatore per avanzare l’animazione
	public int spriteNum = 1;		// Indice del frame corrente
	
	public int getWorldX() {
		
		return this.worldX;
	}
	
	public int getWorldY() {
		
		return this.worldY;
	}
}
