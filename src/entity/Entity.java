package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Entità di base del gioco.
 * Definisce posizione, velocità, direzione e riferimenti ai frame sprite
 * per l’animazione nelle quattro direzioni.
 */

public class Entity {
	
	private int worldX, 		// Coordinata orizzontale
			    worldY;		    // Coordinata verticale
	private int speed;	        // Velocità di movimento
	
	private Rectangle solidArea = new Rectangle(0,0,48,48);
	private int solidAreaDefaultX,solidAreaDefaultY;
	public boolean collisionOn = false;
	
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
	private String direction;		// Direzione corrente
	private int spriteCounter = 0;	// Contatore per avanzare l’animazione
	private int spriteNum = 1;		// Indice del frame corrente
	
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

	public void setSpeed(int value) {
		
		this.speed = value;
	}

	public int getSpeed() {
		
		return this.speed;
	}

	public String getDirection() {
		
		return this.direction;
	}

	public void setDirection(String direction) {
		
		this.direction = direction;
	}

	public int getSpriteNum() {
		
		return this.spriteNum;
	}

	public int getSpriteCounter() {
		
		return this.spriteCounter;
	}

	public void setSpriteCounter(int value) {
		
		this.spriteCounter = value;
	}

	public void setSpriteNum(int value) {
		
		this.spriteNum = value;
	}
	
	public Rectangle getSolidArea() {
	    return solidArea;
	}

	public void setSolidArea(Rectangle r) {
	    this.solidArea = r;
	}
	public int getSolidAreaDefaultX() {
	    return solidAreaDefaultX;
	}

	public void setSolidAreaDefaultX(int solidAreaDefaultX) {
	    this.solidAreaDefaultX = solidAreaDefaultX;
	}

	public int getSolidAreaDefaultY() {
	    return solidAreaDefaultY;
	}

	public void setSolidAreaDefaultY(int solidAreaDefaultY) {
	    this.solidAreaDefaultY = solidAreaDefaultY;
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

	public boolean isCollisionOn() {
	    return collisionOn;
	}

	public void setCollisionOn(boolean collisionOn) {
	    this.collisionOn = collisionOn;
	}
}
