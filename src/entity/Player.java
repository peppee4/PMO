package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

// Classe che rappresenta il personaggio 
public class Player extends Entity{
	private GamePanel gp;		// Pannello
	private KeyHandler keyH;	// Variabile per la gestione dell'input dell'untete
	
	private final int centerX;	// Coordinate centrali dello schermo
	private final int centerY;	// Coordinate centrali dello schermo

	private double life;  		// Vita del player
	
	// Costruttore della classe Player
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;														// Inizializza il pannello
		this.keyH = keyH;													// Inizializza il gestore degli eventi della tastiera										
		
		this.centerX = gp.getScreenWidth()/2 - (gp.getTileSize()/2);		// Calcola la coordinata centrale orizzontale dello schermo
		this.centerY = gp.getScreenHeight()/2 - (gp.getTileSize()/2);		// Calcola la coordinata centrale verticale dello schermo

		this.life = 3.0;													// Imposta la vita iniziale del player
		
		// Imposta l'area solida per la collisione
		setSolidArea(new Rectangle());
		setSolidAreaX(8);
		setSolidAreaY(8);
		
		// Dimensioni dell'area solida
		setSolidAreaWidth(20);
		setSolidAreaHeight(20);
		
		// Posizione di default dell'area solida
		setSolidAreaDefaultX(getSolidArea().x);
		setSolidAreaDefaultY(getSolidArea().y);
		
		// Inizializza le variabili del player
		setDefaultValues();
		getPlayerImage();
	}
	
	// Metodo per inizializzare il player
	public void setDefaultValues() {
		this.setWorldX(gp.getTileSize() * 23); 				// Coordinata x iniziale del Player
		this.setWorldY(gp.getTileSize() * 24); 				// Coordinata y iniziale del Player
		this.setSpeed(3);				            	// Velocità iniziale del Player
		this.setDirection("right");				// Direzione iniziale del Player
		
	}
	
	// Metodo per caricare le sprite del player
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_2.png"));
			stop = ImageIO.read(getClass().getResourceAsStream("/player/player.png"));
		}catch(IOException e) {
			System.out.println(e);
		}
	}
	
	// Metodo per la gestione del movimento del player
	public void update() {
		
		// Ottiene le coordinate attuali del player
		int worldX = getWorldX();
		int worldY = getWorldY();

		if(keyH.upPressed == true || keyH.downPressed == true 
				|| keyH.leftPressed == true || keyH.rightPressed == true) {

			// Gestione dell'input
			if(keyH.upPressed == true){
				this.setDirection("up");				
			}else if(keyH.downPressed == true){
				this.setDirection("down");				
			}else if(keyH.leftPressed == true){
				this.setDirection("left");				
			}else if(keyH.rightPressed == true){
				this.setDirection("right");
			}
			
			// Controllo per la collisione con i tile della mappa(muri)
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// Se il player non tocca Nessun tile "solido può muoversi
			if(collisionOn == false) {
				switch(this.getDirection()) {
					case "up": 
						setWorldY(worldY -= getSpeed());
						break;
					case "down": 
						setWorldY(worldY += getSpeed());
						break;
					case "left":
						setWorldX(worldX -= getSpeed());
						break;
					case "right":
						setWorldX(worldX += getSpeed());
						break;
				}
			}
		
			// Gestione degli sprite
			this.setSpriteCounter(this.getSpriteCounter() + 1);
			if(this.getSpriteCounter() > 10) {
				if(this.getSpriteNum() == 1) {
					this.setSpriteNum(2);
				}else if(this.getSpriteNum() == 2) {
					this.setSpriteNum(1);
				}
			
				this.setSpriteCounter(0);
			}
		}else {
			this.setDirection("stop");
		}
	}
	
	// Metodo per ridisegnare il player
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
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
		
		g2.drawImage(image, centerX, centerY, 30, 30, null);
	}

	// Getter e Setter
	public int getCenterX() {
		return this.centerX;
	}

	public int getCenterY() {
		return this.centerY;
	}

	public double getLife() {
		return this.life;
	}

	public void setLife(double life) {
		this.life = life;
	}
}
