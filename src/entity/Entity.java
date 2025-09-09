package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;



/**
 * Entità di base del gioco.
 * Definisce posizione, velocità, direzione e riferimenti ai frame sprite
 * per l’animazione nelle quattro direzioni.
 */

public class Entity {
	
	protected int worldX;		    // Coordinata verticale
	protected int worldY;
	private double speed;	    // Velocità di movimento
	
	protected Rectangle solidArea = new Rectangle(0,0,48,48);	// Area solida per la collisione/
	protected int solidAreaDefaultX,
				  solidAreaDefaultY;											// Posizione di default dell'area solida
	protected boolean collisionOn = false;										// Flag per la collisione
	
	// Frame dell’animazione per ciascuna direzione
	protected BufferedImage up1, 
					        up2, 
					        down1, 
					        down2, 
					        left1, 
					        left2, 
					        right1, 
					      	right2,
					      	stop;
	private String direction;			// Direzione corrente
	protected int spriteCounter = 0;	// Contatore per avanzare l’animazione
	protected int spriteNum = 1;		// Indice del frame corrente
	
	// Getter e Setter 
	public int getWorldX() {
		
		return this.worldX;
	}
	
	public int getWorldY() {
		
		return this.worldY;
	}
	
	public void setWorldY(int value) {
		
		this.worldY = value;
	}
	
	public void setWorldX(int value) {
		
		this.worldX = value;
	}

	public void setSpeed(double value) {
		
		this.speed = value;
	}
 
	public double getSpeed() {
		
		return this.speed;
	}

	public String getDirection() {
		
		return this.direction;
	}

	public void setDirection(String direction) {
		
		this.direction = direction;
	}

	public int getSolidAreaX() {
	    return solidArea.x;
	}

	public void setSolidAreaX(int x) {
	    solidArea.x = x;
	}

	public int getSolidAreaY() {
	    return solidArea.y;
	}

	public void setSolidAreaY(int y) {
	    solidArea.y = y;
	}
	 
	public int getSolidAreaWidth() {
	    return solidArea.width;
	}

	public void setSolidAreaWidth(int width) {
	    solidArea.width = width;
	}

	public int getSolidAreaHeight() {
	    return solidArea.height;
	}

	public void setSolidAreaHeight(int height) {
	    solidArea.height = height;
	}

	public void setCollisionOn(boolean collisionOn) {
	    this.collisionOn = collisionOn;
	}
}
