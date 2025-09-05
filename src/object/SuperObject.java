package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class SuperObject {
    protected BufferedImage image;         						// Immagine dell'oggetto
    protected String name;                 						// Nome dell'oggetto 
    protected boolean collision = false;   						// Indica se l'oggetto ha una collisione attiva
    private int worldX, worldY;          						// Posizione dell'oggetto nel mondo di gioco
    protected Rectangle solidArea = new Rectangle(0,0,48,48);	// Area solida per la collisione
    protected int solidAreaDefaultX,
	  			  solidAreaDefaultY;							// Posizione di default dell'area solida
    
    // Metodo per disegnare l'oggetto
    public void draw(Graphics2D g2, GamePanel gp){
        int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getCenterX();
		int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getCenterY();
		
        // Disegna solo i tile che si trovano entro i confini dello schermo
		if(worldX + gp.getTileSize() > gp.getPlayer().getWorldX() - gp.getPlayer().getCenterX() &&
		   worldX - gp.getTileSize() < gp.getPlayer().getWorldX() + gp.getPlayer().getCenterX() &&
		   worldY + gp.getTileSize() > gp.getPlayer().getWorldY()- gp.getPlayer().getCenterY() &&
           	worldY - gp.getTileSize() < gp.getPlayer().getWorldY() + gp.getPlayer().getCenterY()) {
				
			// Disegna l’immagine della tile sullo schermo
		    g2.drawImage(image, screenX, screenY, 30, 30, null);
		}
    }
    
    // Funzione per l'interazione con il player
    public void update(GamePanel gp) {
    	if(this != null && gp.cChecker.isPlayerNearObject(this)) {
    		System.out.println("Premi E per interaggire");
    	}
    }
    
    // Getter e Setter
    public int getWorldX() {
    	return this.worldX;
    }
    
    public int getWorldY() {
    	return this.worldY;
    }
    
    public void setWorldX(int value) {
    	this.worldX = value;
    }
    
    public void setWorldY(int value) {
    	this.worldY = value;
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
}
