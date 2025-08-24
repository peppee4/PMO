package entity;

import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Monsters extends Entity {
    public BufferedImage image;        	// Immagine del mostro
    //public int worldX, worldY;         	// Posizione del mostro nel mondo di gioco
    private String name;               	// Nome del mostro
	private int actionLockCounter = 0;	// Contatore per gestire le azioni del mostro
    private GamePanel gp;             	// Riferimento al GamePanel

    // Costruttore
    public Monsters(String name, GamePanel gp) {
        this.name = name;
		this.gp = gp;

		setSolidArea(new Rectangle());
		setSolidAreaX(8);
		setSolidAreaY(8);

		setSolidAreaWidth(30);
		setSolidAreaHeight(45);

		setSolidAreaDefaultX(getSolidArea().x);
		setSolidAreaDefaultY(getSolidArea().y);

		this.setSpeed(3);
    }

    public void draw(Graphics2D g2, GamePanel gp){
        int screenX = getWorldX() - gp.player.getWorldX() + gp.player.getCenterX();
		int screenY = getWorldY() - gp.player.getWorldY() + gp.player.getCenterY();
		
        // Disegna solo i tile che si trovano entro i confini dello schermo
		if(getWorldX() + gp.getTileSize() > gp.player.getWorldX() - gp.player.getCenterX() &&
		    getWorldX() - gp.getTileSize() < gp.player.getWorldX() + gp.player.getCenterX() &&
		    getWorldY() + gp.getTileSize() > gp.player.getWorldY()- gp.player.getCenterY() &&
            getWorldY() - gp.getTileSize() < gp.player.getWorldY() + gp.player.getCenterY()) {
			
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

	public void update() {
		setAction();

		int worldX = this.getWorldX();
		int worldY = this.getWorldY();

		// Collisione con i tile
		collisionOn = false;
		gp.cChecker.checkTile(this);

		// Se non c’è collisione, il mostro si muove
		if(collisionOn == false) {
			switch(this.getDirection()) {
				case "up":
					this.setWorldY(this.getWorldY() - this.getSpeed());
					break;
				case "down":
					this.setWorldY(this.getWorldY() + this.getSpeed());
					break;
				case "left":
					this.setWorldX(this.getWorldX() - this.getSpeed());
					break;
				case "right":
					this.setWorldX(this.getWorldX() + this.getSpeed());
					break;
			}
		}

		// Gestione dell’animazione del mostro
		this.setSpriteCounter(this.getSpriteCounter() + 1);
		if(this.getSpriteCounter() > 12) {
			if(this.getSpriteNum() == 1) {
				this.setSpriteNum(2);
			}else if(this.getSpriteNum() == 2) {
				this.setSpriteNum(1);
			}
			
			this.setSpriteCounter(0);
		}
	}

		// Metodo per definire il comportamento del mostro
	public void setAction() {
		this.actionLockCounter++;

		if(this.actionLockCounter == 200 || collisionOn == true) {
			int random = (int)(Math.random() * 100) + 1; // Numero casuale tra 1 e 100

			// Cambia direzione in base al numero casuale
			if(random <= 25) {
				this.setDirection("up");
			}
			if(random > 25 && random <= 50) {
				this.setDirection("down");
			}
			if(random > 50 && random <= 75) {
				this.setDirection("left");
			}
			if(random > 75 && random <= 100) {
				this.setDirection("right");
			}

			// Resetta il contatore
			this.actionLockCounter = 0;
		}
	}
}
