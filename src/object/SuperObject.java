package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.ImageScaler;
import main.KeyHandler;

public abstract class SuperObject {
	
	private GamePanel gp;										// Riferimento al GamePanel
    protected BufferedImage image1,
    						image2,
    						image3;         					// Immagine dell'oggetto
    protected String name;                 						// Nome dell'oggetto 
    protected boolean collision = false;   						// Indica se l'oggetto ha una collisione attiva
    private int worldX, worldY;          						// Posizione dell'oggetto nel mondo di gioco
    protected Rectangle solidArea = new Rectangle(0,0,48,48);	// Area solida per la collisione
    protected int solidAreaDefaultX,
	  			  solidAreaDefaultY;							// Posizione di default dell'area solida
    private boolean objStatus = false;							// Stato dell'oggetto
    private KeyHandler keyH;									// Riferimento al KeyHandler
    protected ImageScaler iScaler;
    
    public SuperObject(KeyHandler keyH, GamePanel gp){
    	this.keyH = keyH;
    	this.gp = gp;
    }
    
    public SuperObject(GamePanel gp){
    	this.gp = gp;
    	this.iScaler = new ImageScaler();
    }
    
    // Metodo per disegnare l'oggetto
    public void draw(Graphics2D g2){
        int screenX = worldX - this.gp.getPlayer().getWorldX() + this.gp.getPlayer().getCenterX();
		int screenY = worldY - this.gp.getPlayer().getWorldY() + this.gp.getPlayer().getCenterY();
		
        // Disegna solo i tile che si trovano entro i confini dello schermo
		if(worldX + this.gp.getTileSize() > this.gp.getPlayer().getWorldX() - this.gp.getPlayer().getCenterX() &&
		   worldX - this.gp.getTileSize() < this.gp.getPlayer().getWorldX() + this.gp.getPlayer().getCenterX() &&
		   worldY + this.gp.getTileSize() > this.gp.getPlayer().getWorldY()- this.gp.getPlayer().getCenterY() &&
           worldY - this.gp.getTileSize() < this.gp.getPlayer().getWorldY() + this.gp.getPlayer().getCenterY()) {
				
			// Disegna l’immagine della tile sullo schermo
		    if(objStatus == false) {
		    	g2.drawImage(this.image1, screenX, screenY, 30, 30, null);
		    }else {
		    	g2.drawImage(this.image2, screenX, screenY, 30, 30, null);
		    }
		}
    }
    
    // Metodo per l'interazione con il player
    public void update() {
    	if((this != null && this.gp.cChecker.isPlayerNearObject(this)) && (this.objStatus == false)) {
    		this.gp.setGameState(this.gp.dialogueState);
    		
    		if(keyH.ePressed == true) {
    			this.objStatus = true;
    			this.gp.setNumberOfKey(this.gp.getNumberOfKey() + 1);
    			this.gp.setGameState(this.gp.playState);
    		}
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
	
	public void setObjStatus(boolean value) {
		this.objStatus = value;
	}

	public BufferedImage getImage1() {
		return image1;
	}

	public BufferedImage getImage2() {
		return image2;
	}

	public BufferedImage getImage3() {
		return image3;
	}
	
	
}
