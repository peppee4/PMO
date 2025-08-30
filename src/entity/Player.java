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

	private int slowtime;       // Tempo di rallentamento del player

	protected double life;  	// Vita del player
	private double startSpeed;	// Velocità iniziale del player
	
	// Costruttore della classe Player
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;														// Inizializza il pannello
		this.keyH = keyH;													// Inizializza il gestore degli eventi della tastiera										
		
		this.centerX = gp.getScreenWidth()/2 - (gp.getTileSize()/2);		// Calcola la coordinata centrale orizzontale dello schermo
		this.centerY = gp.getScreenHeight()/2 - (gp.getTileSize()/2);		// Calcola la coordinata centrale verticale dello schermo

		this.life = 3.0;													// Imposta la vita iniziale del player
		
		this.slowtime = 0;													// Inizializziamo il tempo di rallentamento

		// Imposta l'area solida per la collisione
		solidArea = new Rectangle();
		setSolidAreaX(8);
		setSolidAreaY(8);
		
		// Dimensioni dell'area solida
		setSolidAreaWidth(20);
		setSolidAreaHeight(20);
		
		// Posizione di default dell'area solida
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		// Inizializza le variabili del player
		setDefaultValues();
		getPlayerImage();

		// Inizializzazione della velocità iniziale del player
		this.startSpeed = this.getSpeed();
	}
	
	// Metodo per inizializzare il player
	public void setDefaultValues() {
		this.setWorldX(gp.getTileSize() * 23); 				// Coordinata x iniziale del Player
		this.setWorldY(gp.getTileSize() * 24); 				// Coordinata y iniziale del Player
		this.setSpeed(3);				            	// Velocità del Player
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
			this.spriteCounter++;
			if(this.spriteCounter > 10) {
				if(this.spriteNum == 1) {
					this.spriteNum = 2;
				}else if(this.spriteNum == 2) {
					this.spriteNum = 1;
				}
			
				this.spriteCounter = 0;
			}
		}else {
			this.setDirection("stop");
		}

		//System.out.println(life);
	}
	
	// Metodo per ridisegnare il player
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		switch(this.getDirection()) {
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
		
		g2.drawImage(image, centerX, centerY, 30, 30, null);
	}

	// Effetto dello slime sul player
	public void isSlow(int value){
		
		// Se il player si trova su almeno uno slime rallentalo
		if(value > 0 || this.slowtime > 0){
			this.setSpeed(1);
			if(this.slowtime < 120){
				this.slowtime++;
			}else{
				this.slowtime = 0;
			}
		}else{
			this.setSpeed(this.startSpeed);
		}
	}

	// Getter e Setter
	public int getCenterX() {
		return this.centerX;
	}

	public int getCenterY() {
		return this.centerY;
	}
	
	public BufferedImage getImageIdle() {
		return this.stop;
	}
	
}
