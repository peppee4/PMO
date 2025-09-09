package entity;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

import main.GamePanel;

public class ExplosiveMonster extends Monsters {
	private GamePanel gp;       // Riferimento al GamePanel
	private boolean flag;		// Flag per il controllo dell'attacco
	
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
	        this.setSpeed(2.7);
	        this.gp.playSoundEffect(9);
	        this.flag = false;
	    }else if(distance > 3){
	    	this.setSpeed(1.5);
	    	this.flag = true;
	    }
    }
    
    public void explosion() {
    	this.gp.playSoundEffect(10);
    	alive = false;
    }
}
