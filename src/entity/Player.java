package entity;

import java.awt.Graphics2D;
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
	
	// Costruttore della classe Player
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		this.centerX = gp.getScreenWidth()/2 - (gp.getTileSize()/2);
		this.centerY = gp.getScreenHeight()/2 - (gp.getTileSize()/2);
		
		setDefaultValues();
		getPlayerImage();
	}
	
	// Metodo per inizializzare il player
	public void setDefaultValues() {
		this.setWorldX(gp.getTileSize() * 23); 				// Coordinata x iniziale del Player
		this.setWorldY(gp.getTileSize() * 23); 				// Coordinata y iniziale del Player
		this.setSpeed(8);				            	// Velocità iniziale del Player
		this.setDirection("right");				// Direzione iniziale del Player
		
	}
	
	// Metodo per caricare le sprite del player
	public void getPlayerImage() {
		try {
			this.setBufferedImage("up1", ImageIO.read(getClass().getResourceAsStream("/player/player_up_1.png")));;
			this.setBufferedImage("up2", ImageIO.read(getClass().getResourceAsStream("/player/player_up_2.png")));
			this.setBufferedImage("down1", ImageIO.read(getClass().getResourceAsStream("/player/player_down_1.png")));
			this.setBufferedImage("down2", ImageIO.read(getClass().getResourceAsStream("/player/player_down_2.png")));
			this.setBufferedImage("left1", ImageIO.read(getClass().getResourceAsStream("/player/player_left_1.png")));
			this.setBufferedImage("left2", ImageIO.read(getClass().getResourceAsStream("/player/player_left_2.png")));
			this.setBufferedImage("right1", ImageIO.read(getClass().getResourceAsStream("/player/player_right_1.png")));
			this.setBufferedImage("right2", ImageIO.read(getClass().getResourceAsStream("/player/player_right_2.png")));
			this.setBufferedImage("stop", ImageIO.read(getClass().getResourceAsStream("/player/player.png")));
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
				setWorldY(worldY -= getSpeed());
			}else if(keyH.downPressed == true){
				this.setDirection("down");
				setWorldY(worldY += getSpeed());
			}else if(keyH.leftPressed == true){
				this.setDirection("left");
				setWorldX(worldX -= getSpeed());
			}else if(keyH.rightPressed == true){
				this.setDirection("right");
				setWorldX(worldX += getSpeed());
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
					image = getBufferedImage("up1");
				}else if(this.getSpriteNum() == 2) {
					image = getBufferedImage("up2");
				}
				break;
			case "down":
				if(this.getSpriteNum() == 1) {
					image = getBufferedImage("down1");
				}else if(this.getSpriteNum() == 2) {
					image = getBufferedImage("down2");
				}
				break;
			case "left":
				if(this.getSpriteNum() == 1) {
					image = getBufferedImage("left1");
				}else if(this.getSpriteNum() == 2) {
					image = getBufferedImage("left2");
				}
				break;
			case "right":
				if(this.getSpriteNum() == 1) {
					image = getBufferedImage("right1");
				}else if(this.getSpriteNum() == 2) {
					image = getBufferedImage("right2");
				}
				break;
			default:
				image = getBufferedImage("stop");
				break;
		}
		
		g2.drawImage(image, centerX, centerY, gp.getTileSize(), gp.getTileSize(), null);
	}

	// Metodi getter per ottenere le coordinate centrali dello schermo
	public int getCenterX() {
		return this.centerX;
	}

	public int getCenterY() {
		return this.centerY;
	}
}
