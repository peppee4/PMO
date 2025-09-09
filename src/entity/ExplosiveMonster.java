package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

import main.GamePanel;

public class ExplosiveMonster extends Monsters {
	private GamePanel gp;       			// Riferimento al GamePanel
	private boolean flag;					// Flag per il controllo dell'attacco
	private boolean exploding = false;
	private int explosionFrame = 0;
	private int explosionCounter = 0;
	private BufferedImage[] explosionSprites = new BufferedImage[5];
	
    // Costruttore
    public ExplosiveMonster(GamePanel gp){
        super("ExplosiveMonster", gp);
        setup();

        this.gp = gp;
        this.flag = true;
    }

    private void setup(){
        // Carica l'immagine del mostro
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/monsters/ExplosiveMonster/ExplosiveMonster_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/monsters/ExplosiveMonster/ExplosiveMonster_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/monsters/ExplosiveMonster/ExplosiveMonster_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/monsters/ExplosiveMonster/ExplosiveMonster_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/monsters/ExplosiveMonster/ExplosiveMonster_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/monsters/ExplosiveMonster/ExplosiveMonster_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/monsters/ExplosiveMonster/ExplosiveMonster_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/monsters/ExplosiveMonster/ExplosiveMonster_right_2.png"));
			explosionSprites[0] = ImageIO.read(getClass().getResourceAsStream("/monsters/monstersElements/explosion_1.png"));
	        explosionSprites[1] = ImageIO.read(getClass().getResourceAsStream("/monsters/monstersElements/explosion_2.png"));
	        explosionSprites[2] = ImageIO.read(getClass().getResourceAsStream("/monsters/monstersElements/explosion_3.png"));
	        explosionSprites[3] = ImageIO.read(getClass().getResourceAsStream("/monsters/monstersElements/explosion_4.png"));
	        explosionSprites[4] = ImageIO.read(getClass().getResourceAsStream("/monsters/monstersElements/explosion_5.png"));

        } catch (IOException e) {
            System.out.println(e);
        }

        // Impostiamo le dimensioni
        width = 30;
		height = 30;

        // Impostiamo il danno
		damage = 1.5;

        // Imposta l'area solida per la collisione
		setSolidAreaX(0);
		setSolidAreaY(0);

		// Dimensioni dell'area solida
		setSolidAreaWidth(35);
		setSolidAreaHeight(20);

		// Posizione di default dell'area solida
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		// Imposta la velocità del mostro
		this.setSpeed(1.5);

        this.setDirection("left");
        
        // Impostiamo il mostro su vivo
        alive = true;
    }
    
    @Override
    public void update() {
        if (exploding) {
            // Gestione animazione esplosione
            explosionCounter++;
            if (explosionCounter % 10 == 0) {
                explosionFrame++;
                if (explosionFrame >= explosionSprites.length) {
                    alive = false;
                    return;
                }
            }
            return; // Non fare altro mentre esplode
        }

        // Se non esplode, esegue la logica standard dei mostri
        super.update();
    }
    
    public void attack() {
    	// Coordinate tile di mostro e player
	    int monsterTileX = this.getWorldX() / gp.getTileSize();
	    int monsterTileY = this.getWorldY() / gp.getTileSize();
	    int playerTileX = gp.getPlayer().getWorldX() / gp.getTileSize();
	    int playerTileY = gp.getPlayer().getWorldY() / gp.getTileSize();

	    // Calcola distanza in tile usando il metodo BFS già esistente
	    int distance = getTileDistance(monsterTileX, monsterTileY, playerTileX, playerTileY);
	    
	    // Se il player non è raggiungibile, esci
	    if (distance == Integer.MAX_VALUE){
	    	return;
		}

	    // Se entro la distanza massima
	    if (distance <= 3 && this.flag) {
	        this.setSpeed(2.9);
	        this.gp.playSoundEffect(9);
	        this.flag = false;
	    }else if(distance > 3){
	    	this.setSpeed(1.5);
	    	this.flag = true;
	    }
    }
    
    public void explosion() {
        gp.playSoundEffect(10); // suono
        exploding = true;       // attiva stato esplosione
        explosionFrame = 0;     // parte dal primo sprite
        explosionCounter = 0;
    }

    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
    	int screenX = getWorldX() - gp.getPlayer().getWorldX() + gp.getPlayer().getCenterX();
		int screenY = getWorldY() - gp.getPlayer().getWorldY() + gp.getPlayer().getCenterY();
		
		if(getWorldX() + gp.getTileSize() > gp.getPlayer().getWorldX() - gp.getPlayer().getCenterX() &&
			    getWorldX() - gp.getTileSize() < gp.getPlayer().getWorldX() + gp.getPlayer().getCenterX() &&
			    getWorldY() + gp.getTileSize() > gp.getPlayer().getWorldY()- gp.getPlayer().getCenterY() &&
	            getWorldY() - gp.getTileSize() < gp.getPlayer().getWorldY() + gp.getPlayer().getCenterY()) {
		
			if (exploding) {
				// Disegna il frame corrente dell'esplosione
				g2.drawImage(explosionSprites[explosionFrame], screenX, screenY, width, height, null);
			} else {
				// Comportamento normale: usa il draw della superclasse
				super.draw(g2, gp);
        	}
		}
    }
}
