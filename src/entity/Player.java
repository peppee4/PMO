package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

// Classe che rappresenta il personaggio 
public class Player extends Entity{
	GamePanel gp;		// Pannello
	KeyHandler keyH;	// Variabile per la gestione dell'input dell'untete
	
	// Costruttore della classe Player
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	// Metodo per inizializzare il player
	public void setDefaultValues() {
		x = 100;				// Coordinata x iniziale del Player
		y = 100;				// Coordinata y iniziale del Player
		speed = 4;				// Velocità iniziale del Player
		direction = "right";
		
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
		
		if(keyH.upPressed == true || keyH.downPressed == true 
				|| keyH.leftPressed == true || keyH.rightPressed == true) {
			// Gestione dell'input
			if(keyH.upPressed == true){
				direction = "up";
				y -= speed;
			}else if(keyH.downPressed == true){
				direction = "down";
				y += speed;
			}else if(keyH.leftPressed == true){
				direction = "left";
				x -= speed;
			}else if(keyH.rightPressed == true){
				direction = "right";
				x += speed;
			}
		
			// Gestione degli sprite
			spriteCounter++;
			if(spriteCounter > 10) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}else if(spriteNum == 2) {
					spriteNum = 1;
				}
			
				spriteCounter = 0;
			}
		}else {
			direction = "stop";
		}
	}
	
	// Metodo per ridisegnare il player
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		
		switch(direction) {
			case "up":
				if(spriteNum == 1) {
					image = up1;
				}else if(spriteNum == 2) {
					image = up2;
				}
				break;
			case "down":
				if(spriteNum == 1) {
					image = down1;
				}else if(spriteNum == 2) {
					image = down2;
				}
				break;
			case "left":
				if(spriteNum == 1) {
					image = left1;
				}else if(spriteNum == 2) {
					image = left2;
				}
				break;
			case "right":
				if(spriteNum == 1) {
					image = right1;
				}else if(spriteNum == 2) {
					image = right2;
				}
				break;
			default:
				image = stop;
				break;
		}
		
		g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
	}
}
