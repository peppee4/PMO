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
	
	protected GamePanel gp;										// Riferimento al GamePanel
    protected BufferedImage image1,
    						image2,
    						image3;         					// Immagine dell'oggetto
    protected String name;                 						// Nome dell'oggetto 
    private boolean collision = false;   						// Indica se l'oggetto ha una collisione attiva
    private int worldX, worldY;          						// Posizione dell'oggetto nel mondo di gioco
    private Rectangle solidArea = new Rectangle(0,0,48,48);	// Area solida per la collisione
    private int solidAreaDefaultX;							// Posizione di default dell'area solida
	private int solidAreaDefaultY;
    private boolean objStatus = false;						// Stato dell'oggetto
    private ImageScaler iScaler;
    private int width;										// Altezza dell'oggetto
	private int height;
    
    public SuperObject(GamePanel gp){
    	this.gp = gp;
    	this.setiScaler(new ImageScaler());
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
		    if(isObjStatus() == false) {
		    	g2.drawImage(this.image1, screenX, screenY, getWidth(), getHeight(), null);
		    }else {
		    	g2.drawImage(this.image2, screenX, screenY, getWidth(), getHeight(), null);
		    }
		}
    }
    
    // Metodo per l'interazione con il player
    public void update() {
    	if((this != null && this.gp.getCChecker().isPlayerNearObject(this)) && (this.isObjStatus() == false)) {
    		this.gp.setGameState(this.gp.getDialogueState());
    		
    		if(this.gp.getKeyH().isePressed()) {
    			this.setObjStatus(true);
    			this.gp.setGameState(this.gp.getPlayState());
    		}
    	}
    }

    
    
    // ---- Getter e Setter ----
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
	    return getSolidArea().x;
	}

	public void setSolidAreaX(int x) {
	    getSolidArea().x = x;
	}

	public int getSolidAreaY() {
	    return getSolidArea().y;
	}

	public void setSolidAreaY(int y) {
	    getSolidArea().y = y;
	}
	 
	public int getSolidAreaWidth() {
	    return getSolidArea().width;
	}

	public void setSolidAreaWidth(int width) {
	    getSolidArea().width = width;
	}

	public int getSolidAreaHeight() {
	    return getSolidArea().height;
	}

	public void setSolidAreaHeight(int height) {
	    getSolidArea().height = height;
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

	public String getName() {
		return name;
	}

	public Rectangle getSolidArea() {
		return solidArea;
	}

	public void setSolidArea(Rectangle solidArea) {
		this.solidArea = solidArea;
	}

	public boolean isObjStatus() {
		return objStatus;
	}

	public boolean isCollision() {
		return collision;
	}

	public void setCollision(boolean collision) {
		this.collision = collision;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
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

	public ImageScaler getiScaler() {
		return iScaler;
	}

	public void setiScaler(ImageScaler iScaler) {
		this.iScaler = iScaler;
	}
	
	
}
